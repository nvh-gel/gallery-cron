package com.eden.cron.mapper;

import com.eden.cron.model.Model;
import com.eden.cron.model.Nickname;
import com.eden.cron.viewmodel.NicknameVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NicknameMapper extends BaseMapper<Nickname, NicknameVM> {

    @Override
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "model", source = "modelId", qualifiedByName = "mapModelId")
    Nickname toModel(NicknameVM nicknameVM);

    @Override
    @Mapping(target = "modelId", source = "model", qualifiedByName = "mapModelIdView")
    NicknameVM toViewModel(Nickname nickname);

    @Named("mapModelId")
    default Model mapModelId(Long modelId) {
        Model model = new Model();
        model.setId(modelId);
        return model;
    }

    @Named("mapModelIdView")
    default Long mapModelIdView(Model model) {
        return model.getId();
    }
}

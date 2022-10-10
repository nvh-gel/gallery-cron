package com.eden.cron.mapper;

import com.eden.cron.model.Model;
import com.eden.cron.model.Nickname;
import com.eden.cron.viewmodel.ModelVM;
import com.eden.cron.viewmodel.NicknameVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelMapper extends BaseMapper<Model, ModelVM> {

    @Override
    @Mapping(target = "deleted", ignore = true)
    Model toModel(ModelVM modelVM);

    @Override
    ModelVM toViewModel(Model model);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "model", ignore = true)
    Nickname nicknameVMToNickname(NicknameVM nicknameVM);

    @Mapping(target = "modelId", source = "model", qualifiedByName = "mapModelIdView")
    NicknameVM nicknameToNicknameVM(Nickname nickname);

    @Named("mapModelIdView")
    default Long mapModelIdView(Model model) {
        return model.getId();
    }
}

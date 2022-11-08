package com.eden.cron.mapper;

import com.eden.cron.model.Model;
import com.eden.cron.model.Nickname;
import com.eden.cron.viewmodel.NicknameVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NicknameMapper extends BaseMapper<Nickname, NicknameVM> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "model", source = "modelId", qualifiedByName = "mapModelId")
    Nickname toModel(NicknameVM nicknameVM);

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "modelId", source = "model", qualifiedByName = "mapModelIdView")
    NicknameVM toViewModel(Nickname nickname);

    /**
     * Map model id to model data
     *
     * @param modelId model id
     * @return model
     */
    @Named("mapModelId")
    default Model mapModelId(Long modelId) {
        if (modelId == null) {
            return null;
        }
        Model model = new Model();
        model.setId(modelId);
        return model;
    }

    /**
     * Map model data to model id.
     *
     * @param model model data to map
     * @return model id
     */
    @Named("mapModelIdView")
    default Long mapModelIdView(Model model) {
        return model.getId();
    }
}

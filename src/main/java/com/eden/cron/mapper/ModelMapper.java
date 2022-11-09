package com.eden.cron.mapper;

import com.eden.cron.model.Model;
import com.eden.cron.model.Nickname;
import com.eden.cron.viewmodel.ModelVM;
import com.eden.cron.viewmodel.NicknameVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.*;

/**
 * Mapstruct mapper for models.
 */
@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelMapper extends BaseMapper<Model, ModelVM> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "deleted", ignore = true)
    Model toModel(ModelVM modelVM);

    /**
     * Map children nicknameVM to nickname Model
     *
     * @param nicknameVM view model
     * @return nickname model
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "model", ignore = true)
    Nickname nicknameVMToNickname(NicknameVM nicknameVM);

    /**
     * Map children nickname model to VM
     *
     * @param nickname nickname model
     * @return nickname view model
     */
    @Mapping(target = "modelId", source = "model", qualifiedByName = "mapModelIdView")
    NicknameVM nicknameToNicknameVM(Nickname nickname);

    /**
     * Map nickname model to modelId
     *
     * @param model model data
     * @return modelId
     */
    @Named("mapModelIdView")
    default Long mapModelIdView(Model model) {
        return model.getId();
    }
}

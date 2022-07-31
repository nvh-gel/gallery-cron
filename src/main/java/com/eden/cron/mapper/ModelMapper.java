package com.eden.cron.mapper;

import com.eden.cron.model.Model;
import com.eden.cron.model.ModelNickName;
import com.eden.cron.viewmodel.ModelVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelMapper extends BaseMapper<Model, ModelVM> {

    @Override
    @Mapping(target = "nickNames", source = "nickNames", qualifiedByName = "toNickNameStrings")
    ModelVM toViewModel(Model model);

    @Mapping(target = "nickNames", source = "nickNames", qualifiedByName = "toNickNameModel")
    @Mapping(target = "deleted", ignore = true)
    Model toModel(ModelVM vm);

    @Named("toNickNameModel")
    default List<ModelNickName> toNickNameModel(List<String> nicks) {
        long modelId = 1L;
        return nicks.stream().map(n -> new ModelNickName(modelId, n)).toList();
    }

    @Named("toNickNameStrings")
    default List<String> toNickNameStrings(List<ModelNickName> nicks) {
        return nicks.stream().map(ModelNickName::getNick).toList();
    }
}

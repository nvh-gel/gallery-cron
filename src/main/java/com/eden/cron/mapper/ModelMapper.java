package com.eden.cron.mapper;

import com.eden.cron.model.Model;
import com.eden.cron.viewmodel.ModelVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelMapper extends BaseMapper<Model, ModelVM> {

    @Override
    @Mapping(target = "deleted", ignore = true)
    Model toModel(ModelVM modelVM);
}

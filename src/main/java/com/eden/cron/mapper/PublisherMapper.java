package com.eden.cron.mapper;

import com.eden.cron.model.Publisher;
import com.eden.cron.viewmodel.PublisherVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PublisherMapper extends BaseMapper<Publisher, PublisherVM> {
}

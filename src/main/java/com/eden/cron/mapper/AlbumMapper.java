package com.eden.cron.mapper;

import com.eden.cron.model.Album;
import com.eden.cron.model.Publisher;
import com.eden.cron.viewmodel.AlbumVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Data mapper for album model and dto.
 */
@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlbumMapper extends BaseMapper<Album, AlbumVM> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "publisherId", source = "publisher", qualifiedByName = "mapPublisherToId")
    AlbumVM toViewModel(Album album);

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "publisher", source = "publisherId", qualifiedByName = "mapIdToPublisher")
    @Mapping(target = "deleted", ignore = true)
    Album toModel(AlbumVM albumVM);

    /**
     * Map publisher model to id.
     *
     * @param publisher publisher
     * @return publisher id
     */
    @Named("mapPublisherToId")
    default Long mapPublisherToId(Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return publisher.getId();
    }

    /**
     * Map id to publisher model.
     *
     * @param id publisher id
     * @return publisher model
     */
    @Named("mapIdToPublisher")
    default Publisher mapIdToPublisher(Long id) {
        if (id == null) {
            return null;
        }
        Publisher publisher = new Publisher();
        publisher.setId(id);
        return publisher;
    }
}

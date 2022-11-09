package com.eden.cron.mapper;

import com.eden.cron.model.Album;
import com.eden.cron.model.Publisher;
import com.eden.cron.viewmodel.AlbumVM;
import com.eden.cron.viewmodel.PublisherVM;
import com.eden.data.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Data mapper for publisher.
 */
@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PublisherMapper extends BaseMapper<Publisher, PublisherVM> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Mapping(target = "deleted", ignore = true)
    Publisher toModel(PublisherVM publisherVM);

    /**
     * Map child album to VM.
     *
     * @param album album to map
     * @return album VM
     */
    @Mapping(target = "publisherId", source = "publisher", qualifiedByName = "mapPublisherToId")
    @Mapping(target = "models", ignore = true)
    AlbumVM mapToAlbumVM(Album album);

    /**
     * Map child album publisher to ID.
     *
     * @param publisher publisher data
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
     * Map child album vm to album model
     *
     * @param albumVM album VM
     * @return album model
     */
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "models", ignore = true)
    Album mapToAlbum(AlbumVM albumVM);
}

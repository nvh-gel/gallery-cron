package com.eden.cron.mapper;

import com.eden.cron.model.Album;
import com.eden.cron.model.Model;
import com.eden.cron.model.Nickname;
import com.eden.cron.model.Publisher;
import com.eden.cron.viewmodel.AlbumVM;
import com.eden.cron.viewmodel.ModelVM;
import com.eden.cron.viewmodel.NicknameVM;
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

    /**
     * Map children nickname model to VM.
     *
     * @param nickname nickname model
     * @return nickname view model
     */
    @Mapping(target = "modelId", source = "model", qualifiedByName = "mapModelToId")
    NicknameVM nicknameToNicknameVM(Nickname nickname);

    /**
     * Map nickname model to modelId.
     *
     * @param model model data
     * @return modelId
     */
    @Named("mapModelToId")
    default Long mapModelIdView(Model model) {
        return model.getId();
    }

    /**
     * Map child nickname VMs to nickname models.
     *
     * @param nicknameVM VM to map
     * @return mapped model
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "model", ignore = true)
    Nickname nicknameVMToNickName(NicknameVM nicknameVM);

    /**
     * Map child model VMs to models.
     *
     * @param modelVM VM to map
     * @return mapped model
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "albums", ignore = true)
    Model modelVMToModel(ModelVM modelVM);

    @Mapping(target = "albums", ignore = true)
    ModelVM modelToModelVM(Model model);
}

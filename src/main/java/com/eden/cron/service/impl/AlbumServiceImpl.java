package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.cron.mapper.AlbumMapper;
import com.eden.cron.model.Album;
import com.eden.cron.producer.AlbumProducer;
import com.eden.cron.repository.AlbumRepository;
import com.eden.cron.service.AlbumService;
import com.eden.cron.utils.Constants;
import com.eden.cron.viewmodel.AlbumVM;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementation for album.
 */
@Service
@Log4j2
public class AlbumServiceImpl implements AlbumService {

    private AlbumRepository albumRepository;

    private final AlbumMapper albumMapper = Mappers.getMapper(AlbumMapper.class);

    private AlbumProducer albumProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AlbumVM create(AlbumVM albumVM) {

        Album exist = albumRepository.findFirstByNameEquals(albumVM.getName());
        if (exist == null) {
            Album album = albumMapper.toModel(albumVM);
            album.setCreatedAt(LocalDateTime.now());
            album.setUpdatedAt(LocalDateTime.now());
            Album created = albumRepository.save(album);
            log.info(Constants.PROCESSED_MESSAGE, Action.CREATE, albumVM);
            return albumMapper.toViewModel(created);
        }
        log.info("album {} already exists.", albumVM.getName());
        return albumMapper.toViewModel(exist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(AlbumVM albumVM) {

        return albumProducer.sendProcessingMessageToQueue(Action.CREATE, albumVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AlbumVM> findAll() {

        List<Album> albums = albumRepository.findAll();
        return albumMapper.toViewModel(albums);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AlbumVM findById(Long id) {

        Album album = albumRepository.findById(id).orElse(null);
        return albumMapper.toViewModel(album);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AlbumVM update(AlbumVM albumVM) {

        Album exist = albumRepository.findById(albumVM.getId()).orElse(null);
        if (exist == null) {
            return null;
        }
        Album toUpdate = albumMapper.toModel(albumVM);
        albumMapper.mapUpdate(exist, toUpdate);
        exist.setUpdatedAt(LocalDateTime.now());
        Album updated = albumRepository.save(exist);
        log.info(Constants.PROCESSED_MESSAGE, Action.UPDATE, albumVM);
        return albumMapper.toViewModel(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateOnQueue(AlbumVM albumVM) {

        return albumProducer.sendProcessingMessageToQueue(Action.UPDATE, albumVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AlbumVM delete(Long id) {

        Album exist = albumRepository.findById(id).orElse(null);
        if (exist == null) {
            return null;
        }
        albumRepository.deleteById(id);
        exist.setUpdatedAt(LocalDateTime.now());
        log.info(Constants.PROCESSED_MESSAGE, Action.DELETE, id);
        return albumMapper.toViewModel(exist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteOnQueue(Long id) {

        AlbumVM toDelete = new AlbumVM();
        toDelete.setId(id);
        return albumProducer.sendProcessingMessageToQueue(Action.DELETE, toDelete);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setAlbumRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setAlbumProducer(AlbumProducer albumProducer) {
        this.albumProducer = albumProducer;
    }
}

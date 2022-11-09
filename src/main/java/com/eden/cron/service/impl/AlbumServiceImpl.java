package com.eden.cron.service.impl;

import com.eden.common.utils.Action;
import com.eden.cron.mapper.AlbumMapper;
import com.eden.cron.model.Album;
import com.eden.cron.producer.AlbumProducer;
import com.eden.cron.repository.AlbumRepository;
import com.eden.cron.service.AlbumService;
import com.eden.cron.viewmodel.AlbumVM;
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

        Album album = albumMapper.toModel(albumVM);
        album.setCreatedAt(LocalDateTime.now());
        album.setUpdatedAt(LocalDateTime.now());
        Album created = albumRepository.save(album);
        return albumMapper.toViewModel(created);
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
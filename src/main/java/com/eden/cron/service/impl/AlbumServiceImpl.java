package com.eden.cron.service.impl;

import com.eden.cron.service.AlbumService;
import com.eden.cron.viewmodel.AlbumVM;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Override
    public List<AlbumVM> findAllAlbums() {
        return null;
    }

    @Override
    public AlbumVM findAlbumById(Long id) {
        return null;
    }

    @Override
    public List<AlbumVM> findAlbumByName(String name) {
        return null;
    }

    @Override
    public List<AlbumVM> findAlbumByModelId(Long id) {
        return null;
    }

    @Override
    public List<AlbumVM> findAlbumByModelName(String name) {
        return null;
    }
}

package com.eden.cron.service;

import com.eden.cron.viewmodel.AlbumVM;

import java.util.List;

@SuppressWarnings("unused")
public interface AlbumService {

    List<AlbumVM> findAllAlbums();

    AlbumVM findAlbumById(Long id);

    List<AlbumVM> findAlbumByName(String name);

    List<AlbumVM> findAlbumByModelId(Long id);

    List<AlbumVM> findAlbumByModelName(String name);
}

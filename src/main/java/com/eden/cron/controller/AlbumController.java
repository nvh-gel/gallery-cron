package com.eden.cron.controller;

import com.eden.common.utils.ResponseModel;
import com.eden.cron.service.AlbumService;
import com.eden.cron.viewmodel.AlbumVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for album handling.
 */
@RestController
@RequestMapping("album")
public class AlbumController {

    private AlbumService albumService;

    /**
     * Create an album.
     *
     * @param request creation request data.
     * @return transaction uuid
     */
    @PostMapping
    public ResponseEntity<ResponseModel> createAlbum(@RequestBody AlbumVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.created(albumService.createOnQueue(request)));
    }

    /**
     * Fina all albums.
     *
     * @return list of albums
     */
    @GetMapping
    public ResponseEntity<ResponseModel> findAllAlbums() {

        return ResponseEntity.ok(ResponseModel.ok(albumService.findAll()));
    }

    /**
     * Find a single album by id.
     *
     * @param id album id to find
     * @return found result
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> findAlbumById(@PathVariable Long id) {

        AlbumVM result = albumService.findById(id);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseModel.notFound());
        }
        return ResponseEntity.ok(ResponseModel.ok(result));
    }

    /**
     * Update an album.
     *
     * @param request update request data
     * @return transaction uuid
     */
    @PutMapping
    public ResponseEntity<ResponseModel> updateAlbum(@RequestBody AlbumVM request) {

        return ResponseEntity.accepted()
                .body(ResponseModel.updated(albumService.updateOnQueue(request)));
    }

    /**
     * Delete an album.
     *
     * @param id album id to delete
     * @return transaction uuid
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteAlbum(@PathVariable Long id) {

        return ResponseEntity.accepted()
                .body(ResponseModel.deleted(albumService.deleteOnQueue(id)));
    }

    /**
     * Setter.
     */
    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }
}

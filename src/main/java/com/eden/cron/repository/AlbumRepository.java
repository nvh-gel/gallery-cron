package com.eden.cron.repository;

import com.eden.cron.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for album.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    /**
     * Find the first album that has name matches.
     *
     * @param name name to find
     * @return found album
     */
    Album findFirstByNameEquals(String name);
}

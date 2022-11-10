package com.eden.cron.repository;

import com.eden.cron.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Album findFirstByNameEquals(String name);
}

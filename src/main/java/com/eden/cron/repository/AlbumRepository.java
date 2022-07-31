package com.eden.cron.repository;

import com.eden.cron.model.Album;
import com.eden.data.repository.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends SoftDeleteRepository<Album, Long> {
}

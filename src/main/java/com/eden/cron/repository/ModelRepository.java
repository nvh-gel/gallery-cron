package com.eden.cron.repository;

import com.eden.cron.model.Model;
import com.eden.data.repository.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends SoftDeleteRepository<Model, Long> {
}

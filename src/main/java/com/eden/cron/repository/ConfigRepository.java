package com.eden.cron.repository;

import com.eden.cron.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for config.
 */
@Repository
public interface ConfigRepository extends JpaRepository<Config, String> {
}

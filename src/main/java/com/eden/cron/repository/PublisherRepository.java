package com.eden.cron.repository;

import com.eden.cron.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Publisher.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}

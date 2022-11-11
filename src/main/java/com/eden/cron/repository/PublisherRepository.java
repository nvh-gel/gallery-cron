package com.eden.cron.repository;

import com.eden.cron.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for Publisher.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    /**
     * Select list of publisher names.
     *
     * @return list of names
     */
    @Query("select name from Publisher")
    List<String> findALlName();
}

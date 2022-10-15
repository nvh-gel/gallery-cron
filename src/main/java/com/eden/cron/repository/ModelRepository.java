package com.eden.cron.repository;

import com.eden.cron.model.Model;
import com.eden.data.repository.SoftDeleteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for model.
 */
@Repository
public interface ModelRepository extends SoftDeleteRepository<Model, Long> {

    /**
     * Find model by model name.
     *
     * @param name model name
     * @return list of found models
     */
    List<Model> findAllByNameIgnoreCaseContaining(String name);

    /**
     * Find all models by name, native name or nickname
     *
     * @param name name to find
     * @return list of found models
     */
    @Query("SELECT m FROM Model m LEFT JOIN m.nicks n "
            + "WHERE UPPER(m.name) LIKE UPPER(CONCAT('%', ?1, '%')) "
            + "OR m.nativeName LIKE %?1% "
            + "OR UPPER(n.nick) LIKE UPPER(CONCAT('%', ?1, '%')) "
    )
    List<Model> findAllByName(String name);
}

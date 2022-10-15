package com.eden.cron.repository;

import com.eden.cron.model.Nickname;
import com.eden.data.repository.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for model nickname.
 */
@Repository
public interface NicknameRepository extends SoftDeleteRepository<Nickname, Long> {
}

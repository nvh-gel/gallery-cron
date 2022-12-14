package com.eden.cron.repository;

import com.eden.cron.model.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for model nickname.
 */
@Repository
public interface NicknameRepository extends JpaRepository<Nickname, Long> {
}

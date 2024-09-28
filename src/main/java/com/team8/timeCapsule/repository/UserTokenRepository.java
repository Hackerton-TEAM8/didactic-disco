package com.team8.timeCapsule.repository;

import com.team8.timeCapsule.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByUserId(Long userId);
}

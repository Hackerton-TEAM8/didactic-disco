package com.team8.timeCapsule.repository;


import com.team8.timeCapsule.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {  // String 타입 ID 사용
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
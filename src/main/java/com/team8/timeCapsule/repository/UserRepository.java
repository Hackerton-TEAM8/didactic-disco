package com.team8.timeCapsule.repository;


import com.team8.timeCapsule.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity findByEmail(String email);
    UserEntity findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findById(String id);
    boolean existsByEmail(String email);
}
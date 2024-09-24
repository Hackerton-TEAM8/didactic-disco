package com.team8.timeCapsule.repository;


import com.team8.timeCapsule.domain.TimeCapsule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeCapsuleRepository extends JpaRepository<TimeCapsule, Long> {
}

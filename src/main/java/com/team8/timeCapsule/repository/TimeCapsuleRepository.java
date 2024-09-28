package com.team8.timeCapsule.repository;


import com.team8.timeCapsule.domain.TimeCapsule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeCapsuleRepository extends JpaRepository<TimeCapsule, Long> {

    // 유저 ID를 기반으로 타임캡슐 리스트 조회
    List<TimeCapsule> findByUserId(String userId);
}

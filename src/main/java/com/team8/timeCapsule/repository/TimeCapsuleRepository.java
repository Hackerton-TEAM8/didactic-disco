package com.team8.timeCapsule.repository;


import com.team8.timeCapsule.domain.TimeCapsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeCapsuleRepository extends JpaRepository<TimeCapsule, Long> {

    // 유저 ID를 기반으로 타임캡슐 리스트 조회
    List<TimeCapsule> findByUserId(String userId);

    // 특정 유저의 열린 타임캡슐 조회 (Unlock Date가 현재보다 이전)
    @Query("SELECT t FROM TimeCapsule t WHERE t.userId = :userId AND t.unlockDate <= :currentDate")
    List<TimeCapsule> findOpenedTimeCapsulesByUserId(String userId, LocalDateTime currentDate);

    // 안열린타입캡슐
    @Query("SELECT t FROM TimeCapsule t WHERE t.userId = :userId AND t.unlockDate >= :currentDate")
    List<TimeCapsule> findClosedTimeCapsulesByUserId(String userId, LocalDateTime currentDate);
}

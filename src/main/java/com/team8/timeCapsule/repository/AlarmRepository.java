package com.team8.timeCapsule.repository;

import com.team8.timeCapsule.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    // unlockDate가 현재 시각 이전이면서 확인되지 않은 알람 조회
    List<Alarm> findAllByUnlockDateLessThanEqualAndIsConfirmFalse(LocalDateTime unlockDate);
}
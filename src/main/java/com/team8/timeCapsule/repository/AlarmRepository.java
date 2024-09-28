package com.team8.timeCapsule.repository;

import com.team8.timeCapsule.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByUnlockDateLessThanEqualAndIsConfirmFalse(LocalDateTime unlockDate);
}
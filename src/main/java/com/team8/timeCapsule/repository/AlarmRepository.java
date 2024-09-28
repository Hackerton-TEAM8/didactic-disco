package com.team8.timeCapsule.repository;

import com.team8.timeCapsule.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}

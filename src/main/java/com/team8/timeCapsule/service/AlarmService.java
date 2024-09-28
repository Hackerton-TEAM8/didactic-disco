package com.team8.timeCapsule.service;

import com.team8.timeCapsule.domain.Alarm;
import com.team8.timeCapsule.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    // 알람 목록 조회 메소드
    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAll();
    }

    // 알람 확인 여부 수정 메소드
    public Alarm confirmAlarm(Long alarmId) {
        Optional<Alarm> optionalAlarm = alarmRepository.findById(alarmId);

        if (optionalAlarm.isPresent()) {
            Alarm alarm = optionalAlarm.get();
            alarm.setIsConfirm(true);  // isConfirm을 true로 설정
            return alarmRepository.save(alarm);  // 변경사항 저장
        } else {
            throw new RuntimeException("Alarm not found with id: " + alarmId);
        }
    }

    // 알람 생성
    public Alarm createAlarm(String title, String content, Long timeCapsuleId) {
        Alarm alarm = new Alarm();
        alarm.setTitle(title);
        alarm.setContent(content);
        alarm.setIsConfirm(false); // 기본적으로 확인되지 않은 상태로 설정
        alarm.setTimeCapsuleId(timeCapsuleId);

        return alarmRepository.save(alarm);
    }
}

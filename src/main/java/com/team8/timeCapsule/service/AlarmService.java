package com.team8.timeCapsule.service;

import com.team8.timeCapsule.domain.Alarm;
import com.team8.timeCapsule.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    public Alarm createAlarm(String title, String content, Long timeCapsuleId) {
        Alarm alarm = new Alarm();
        alarm.setTitle(title);
        alarm.setContent(content);
        alarm.setIsConfirm(false); // 기본적으로 확인되지 않은 상태로 설정
        alarm.setTimeCapsuleId(timeCapsuleId);

        return alarmRepository.save(alarm);
    }
}

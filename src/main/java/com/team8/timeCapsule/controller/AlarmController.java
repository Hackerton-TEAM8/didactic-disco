package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.domain.Alarm;
import com.team8.timeCapsule.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    // 알람 목록 조회 API
    @GetMapping
    public List<Alarm> getAllAlarms() {
        return alarmService.getAllAlarms();
    }

    // 알람 확인 여부 수정 API
    @PutMapping("/confirm/{alarmId}")
    public Alarm confirmAlarm(@PathVariable Long alarmId) {
        return alarmService.confirmAlarm(alarmId);
    }

    // 타임캡슐에 대한 알람 생성
    @PostMapping("/{timeCapsuleId}")
    public Alarm createAlarmForTimeCapsule(
            @PathVariable Long timeCapsuleId,
            @RequestParam String title,
            @RequestParam String content) {

        return alarmService.createAlarm(title, content, timeCapsuleId);
    }


}

package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.domain.Alarm;
import com.team8.timeCapsule.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    // 타임캡슐 생성 API
    @PostMapping("/{timeCapsuleId}")
    public Alarm createAlarmForTimeCapsule(
            @PathVariable Long timeCapsuleId,
            @RequestParam String title,
            @RequestParam String content) {

        // 알람 생성 로직 호출
        return alarmService.createAlarm(title, content, timeCapsuleId);
    }


}

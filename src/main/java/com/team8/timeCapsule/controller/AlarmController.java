package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.domain.Alarm;
import com.team8.timeCapsule.dto.AlarmResponse;
import com.team8.timeCapsule.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    // 알람 목록 조회 API
    @GetMapping
    public ResponseEntity<List<AlarmResponse>> getAllAlarms() {
        List<AlarmResponse> alarms = alarmService.getAllAlarms();
        return ResponseEntity.ok(alarms); // 알람 목록을 포함하여 200 OK 반환
    }

    // 알람 확인 여부 수정 API
    @PutMapping("/confirm/{alarmId}")
    public ResponseEntity<AlarmResponse> confirmAlarm(@PathVariable Long alarmId) {
        AlarmResponse updatedAlarm = alarmService.confirmAlarm(alarmId);
        return ResponseEntity.ok(updatedAlarm); // 수정된 알람을 포함하여 200 OK 반환
    }

    // 타임캡슐에 대한 알람 생성 API
    @PostMapping("/{timeCapsuleId}")
    public ResponseEntity<AlarmResponse> createAlarmForTimeCapsule(
            @PathVariable Long timeCapsuleId,
            @RequestParam String title,
            @RequestParam String content) {
        AlarmResponse createdAlarm = alarmService.createAlarm(title, content, timeCapsuleId);
        return ResponseEntity.ok(createdAlarm); // 생성된 알람을 포함하여 200 OK 반환
    }


}
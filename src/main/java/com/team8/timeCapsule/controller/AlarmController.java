package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.dto.AlarmRequest;
import com.team8.timeCapsule.dto.AlarmResponse;
import com.team8.timeCapsule.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alarms")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    // 알람 목록 조회
    @GetMapping
    public ResponseEntity<List<AlarmResponse>> getAllAlarms() {
        List<AlarmResponse> alarms = alarmService.getAllAlarms();
        return new ResponseEntity<>(alarms, HttpStatus.OK);
    }

    // 알람 생성
    @PostMapping
    public ResponseEntity<AlarmResponse> createAlarm(@RequestBody AlarmRequest alarmRequest) {
        AlarmResponse alarmResponse = alarmService.createAlarm(alarmRequest);
        return new ResponseEntity<>(alarmResponse, HttpStatus.CREATED);
    }

    // 알람 확인 여부 수정
    @PutMapping("/{id}/confirm")
    public ResponseEntity<AlarmResponse> confirmAlarm(@PathVariable Long id) {
        AlarmResponse alarmResponse = alarmService.confirmAlarm(id);
        return new ResponseEntity<>(alarmResponse, HttpStatus.OK);
    }
}
package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.dto.TimeCapsuleRequest;
import com.team8.timeCapsule.dto.TimeCapsuleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/myboard")
public class TimeCapsuleController {

    @PostMapping
    public ResponseEntity<String> createTimeCapsule(
            @RequestPart("data") TimeCapsuleRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        timeCapsuleService.createTimeCapsule(request, file);
        return ResponseEntity.ok("타임캡슐 등록 완료");
    }
    @GetMapping
    public ResponseEntity<List<TimeCapsuleResponse>> getAllTimeCapsules() {
        // 타임캡슐 전체 조회 로직
        return ResponseEntity.ok(new ArrayList<>());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteTimeCapsule(@RequestParam Long id) {
        // 타임캡슐 삭제 로직
        return ResponseEntity.ok("타임캡슐 삭제 완료");
    }

    @PutMapping("/revise")
    public ResponseEntity<String> updateTimeCapsule(@RequestParam Long id, @RequestBody TimeCapsuleRequest request) {
        // 타임캡슐 수정 로직
        return ResponseEntity.ok("타임캡슐 수정 완료");
    }
}

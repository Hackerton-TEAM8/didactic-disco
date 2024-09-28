package com.team8.timeCapsule.controller;


import com.team8.timeCapsule.dto.TimeCapsuleRequest;
import com.team8.timeCapsule.dto.TimeCapsuleResponse;
import com.team8.timeCapsule.security.TokenProvider;
import com.team8.timeCapsule.service.TimeCapsuleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/v1/timecapsule")
@RequiredArgsConstructor
public class TimeCapsuleController {

    private final TimeCapsuleService timeCapsuleService;
    private final TokenProvider tokenProvider;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> createTimeCapsule(
            @RequestPart("json") @Valid TimeCapsuleRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        timeCapsuleService.createTimeCapsule(request, file);
        return ResponseEntity.ok("타임캡슐 등록 완료");
    }

    @GetMapping
    public ResponseEntity<List<TimeCapsuleResponse>> getAllTimeCapsules() {
        List<TimeCapsuleResponse> responses = timeCapsuleService.getAllTimeCapsules();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeCapsuleResponse> getTimeCapsule(@PathVariable Long id) {
        TimeCapsuleResponse response = timeCapsuleService.getTimeCapsule(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTimeCapsule(
            @PathVariable Long id,
            @RequestPart("json") TimeCapsuleRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        timeCapsuleService.updateTimeCapsule(id, request, file);
        return ResponseEntity.ok("타임캡슐 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeCapsule(@PathVariable Long id) {
        timeCapsuleService.deleteTimeCapsule(id);
        return ResponseEntity.ok("타임캡슐 삭제 완료");
    }

    //특정 유저의 타입캡슐 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TimeCapsuleResponse>> getTimeCapsulesByUserId(@PathVariable String userId) {  // String 타입으로 수정
        List<TimeCapsuleResponse> responses = timeCapsuleService.getTimeCapsulesByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user")
    public ResponseEntity<List<TimeCapsuleResponse>> getTimeCapsulesByUserId(HttpServletRequest request) {
        // 요청 헤더에서 JWT 토큰을 추출합니다.
        String token = tokenProvider.getTokenFromRequest(request);

        // 토큰에서 userId를 추출합니다.
        String userId = tokenProvider.validateAndGetUserId(token);

        // 추출한 userId를 사용하여 타임캡슐을 조회합니다.
        List<TimeCapsuleResponse> responses = timeCapsuleService.getTimeCapsulesByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    // 특정 유저의 열린 타임캡슐 조회
    @GetMapping("/opened/{userId}")
    public ResponseEntity<List<TimeCapsuleResponse>> getOpenedTimeCapsulesByUserId(@PathVariable String userId) {
        List<TimeCapsuleResponse> responses = timeCapsuleService.getOpenedTimeCapsulesByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    // 특정 유저의 닫힌 타임캡슐 조회
    @GetMapping("/opened/{userId}")
    public ResponseEntity<List<TimeCapsuleResponse>> getClosedTimeCapsulesByUserId(@PathVariable String userId) {
        List<TimeCapsuleResponse> responses = timeCapsuleService.getClosedTimeCapsulesByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/opened")
    public ResponseEntity<List<TimeCapsuleResponse>> getOpenedTimeCapsulesByUserId(HttpServletRequest request) {
        String token = tokenProvider.getTokenFromRequest(request);
        String userId = tokenProvider.validateAndGetUserId(token);

        List<TimeCapsuleResponse> responses = timeCapsuleService.getOpenedTimeCapsulesByUserId(userId);
        return ResponseEntity.ok(responses);
    }
}
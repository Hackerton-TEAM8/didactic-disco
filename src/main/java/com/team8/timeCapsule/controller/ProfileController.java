package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.dto.ProfileRequest;
import com.team8.timeCapsule.dto.ProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    // 프로필 조회
    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        try {
            // 프로필 조회 로직
            ProfileResponse response = new ProfileResponse(); // 여기서 실제 프로필 조회 로직을 구현
            return ResponseEntity.ok(response); // 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    // 프로필 업데이트
    @PutMapping
    public ResponseEntity<String> updateProfile(@RequestBody ProfileRequest request) {
        try {
            // 프로필 업데이트 로직
            // 여기서 실제 프로필 업데이트 로직을 구현
            return ResponseEntity.ok("프로필 업데이트 완료"); // 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }
}
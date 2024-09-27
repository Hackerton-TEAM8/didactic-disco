package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.dto.ProfileRequest;
import com.team8.timeCapsule.dto.ProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
   //테스트할래4
    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        // 프로필 조회 로직
        return ResponseEntity.ok(new ProfileResponse());
    }

    @PutMapping
    public ResponseEntity<String> updateProfile(@RequestBody ProfileRequest request) {
        // 프로필 업데이트 로직
        return ResponseEntity.ok("프로필 업데이트 완료");
    }
}
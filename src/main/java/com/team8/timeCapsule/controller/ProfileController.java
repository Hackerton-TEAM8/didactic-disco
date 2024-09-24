package com.team8.timeCapsule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

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
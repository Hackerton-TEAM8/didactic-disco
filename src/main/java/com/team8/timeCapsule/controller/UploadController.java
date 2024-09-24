package com.team8.timeCapsule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {

    @PostMapping
    public ResponseEntity<String> selectPhoto() {
        // 사진 선택 로직
        return ResponseEntity.ok("사진 선택 완료");
    }

    @PutMapping
    public ResponseEntity<String> editPhoto() {
        // 사진 편집 로직
        return ResponseEntity.ok("사진 편집 완료");
    }

    @PostMapping("/write")
    public ResponseEntity<String> createPost() {
        // 글 작성 로직
        return ResponseEntity.ok("글 작성 완료");
    }

    @PostMapping("/set-time")
    public ResponseEntity<String> setUploadTime() {
        // 업로드 시기 설정 로직
        return ResponseEntity.ok("업로드 시기 설정 완료");
    }
}
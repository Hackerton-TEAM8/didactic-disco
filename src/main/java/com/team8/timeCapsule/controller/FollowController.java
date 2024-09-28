package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.dto.FriendProfileResponse;
import com.team8.timeCapsule.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import com.team8.timeCapsule.security.TokenProvider;

@RestController
@RequestMapping("/api/v1/friends")
public class FollowController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/list/{id}") // URL 경로 변수로 변경
    public ResponseEntity<FriendProfileResponse> getFriendList(@PathVariable String id) {
        FriendProfileResponse response = friendService.getFriendList(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<FriendProfileResponse> getFriendList(HttpServletRequest request) {
        String token = tokenProvider.getTokenFromRequest(request);
        String userId = tokenProvider.validateAndGetUserId(token);

        FriendProfileResponse response = friendService.getFriendList(id);
        return ResponseEntity.ok(response);
    }
}
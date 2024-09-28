package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.domain.Follow;
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

    private final TokenProvider tokenProvider;

    @Autowired
    private FriendService friendService;

    public FollowController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/list/{id}") // URL 경로 변수로 변경
    public ResponseEntity<FriendProfileResponse> getFriendList(@PathVariable String id) {
        FriendProfileResponse response = friendService.getFriendList(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<FriendProfileResponse> getFriendList(HttpServletRequest request) {
        String token = tokenProvider.getTokenFromRequest(request);
        String id = tokenProvider.validateAndGetUserId(token);

        FriendProfileResponse response = friendService.getFriendList(id);
        return ResponseEntity.ok(response);
    }

    // 친구 요청 보내기
    @PostMapping("/request")
    public ResponseEntity<Follow> sendFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        Follow follow = friendService.sendFriendRequest(senderId, receiverId);
        return ResponseEntity.ok(follow);
    }

    // 친구 요청 수락하기
    @PostMapping("/accept")
    public ResponseEntity<Follow> acceptFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        Follow follow = friendService.acceptFriendRequest(senderId, receiverId);
        return ResponseEntity.ok(follow);
    }

    // 친구 요청 거절하기
    @PostMapping("/reject")
    public ResponseEntity<Void> rejectFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        friendService.rejectFriendRequest(senderId, receiverId);
        return ResponseEntity.noContent().build();
    }

    // 친구 삭제하기
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFriend(@RequestParam String senderId, @RequestParam String receiverId) {
        friendService.deleteFriend(senderId, receiverId);
        return ResponseEntity.noContent().build();
    }
}
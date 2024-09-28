package com.team8.timeCapsule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendProfileRequest {
    private String userId;        // 현재 로그인한 사용자의 ID
}
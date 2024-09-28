package com.team8.timeCapsule.dto;

import com.team8.timeCapsule.domain.FriendRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendProfileResponse {
    private List<FriendDto> friends;  // 친구 목록

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendDto {
        private String friendId;              // 친구 ID
        private String friendName;            // 친구 이름
        private FriendRequestStatus status;   // 친구 요청 상태
    }
}
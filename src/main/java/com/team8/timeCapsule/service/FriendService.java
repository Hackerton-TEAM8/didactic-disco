package com.team8.timeCapsule.service;

import com.team8.timeCapsule.dto.FriendProfileResponse;
import com.team8.timeCapsule.domain.Follow;
import com.team8.timeCapsule.domain.UserEntity;
import com.team8.timeCapsule.domain.FriendRequestStatus; // 추가
import com.team8.timeCapsule.repository.FollowRepository;
import com.team8.timeCapsule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository; // UserRepository 주입

    public FriendProfileResponse getFriendList(String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            // 유저가 없을 경우 적절한 처리
            return FriendProfileResponse.builder().friends(Collections.emptyList()).build();
        }

        // ACCEPT 상태의 FriendRequestStatus를 사용하여 친구 목록을 가져옵니다.
        List<Follow> friends = followRepository.findBySenderOrReceiverAndState(user, user, FriendRequestStatus.ACCEPT);

        // DTO로 변환
        List<FriendProfileResponse.FriendDto> friendDtos = friends.stream()
                .map(follow -> {
                    // 친구 ID 가져오기
                    UserEntity friend = follow.getSender().equals(user) ? follow.getReceiver() : follow.getSender();

                    // 친구 이름 설정
                    String friendName = friend.getUsername(); // UserEntity에서 username을 가져옵니다.

                    return FriendProfileResponse.FriendDto.builder()
                            .friendId(friend.getId()) // 친구 ID 설정
                            .friendName(friendName) // 친구 이름 설정
                            .status(follow.getState()) // 친구 요청 상태 설정
                            .build();
                })
                .collect(Collectors.toList());

        // FriendProfileResponse를 생성하여 친구 목록을 반환합니다.
        return FriendProfileResponse.builder()
                .friends(friendDtos) // 친구 목록 설정
                .build();
    }
}
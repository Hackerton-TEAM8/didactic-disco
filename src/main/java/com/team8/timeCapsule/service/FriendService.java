package com.team8.timeCapsule.service;

import com.team8.timeCapsule.dto.FriendProfileResponse;
import com.team8.timeCapsule.domain.Follow;
import com.team8.timeCapsule.domain.UserEntity;
import com.team8.timeCapsule.domain.FriendRequestStatus;
import com.team8.timeCapsule.repository.FollowRepository;
import com.team8.timeCapsule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public FriendProfileResponse getFriendList(String userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return FriendProfileResponse.builder().friends(Collections.emptyList()).build();
        }

        List<Follow> friends = followRepository.findBySenderOrReceiverAndState(user, user, FriendRequestStatus.ACCEPT);

        List<FriendProfileResponse.FriendDto> friendDtos = friends.stream()
                .map(follow -> {
                    UserEntity friend = follow.getSender().equals(user) ? follow.getReceiver() : follow.getSender();
                    String friendName = friend.getUsername();

                    return FriendProfileResponse.FriendDto.builder()
                            .friendId(friend.getId())
                            .friendName(friendName)
                            .status(follow.getState())
                            .build();
                })
                .collect(Collectors.toList());

        return FriendProfileResponse.builder()
                .friends(friendDtos)
                .build();
    }

    public Follow sendFriendRequest(String senderId, String receiverId) {
        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        UserEntity receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        Follow follow = new Follow();
        follow.setSender(sender);
        follow.setReceiver(receiver);
        follow.setState(FriendRequestStatus.RECEIVE);

        return followRepository.save(follow);
    }

    public void acceptFriendRequest(String senderId, String receiverId) {
        // 친구 요청을 수락할 때 양방향 관계를 생성
        Optional<Follow> followOpt = followRepository.findBySenderAndReceiverAndState(
                userRepository.findById(senderId).orElse(null),
                userRepository.findById(receiverId).orElse(null),
                FriendRequestStatus.RECEIVE);

        if (followOpt.isPresent()) {
            Follow follow = followOpt.get();
            follow.setState(FriendRequestStatus.ACCEPT);
            followRepository.save(follow); // A -> B 관계

            // B -> A 관계 추가
            Follow reverseFollow = new Follow();
            reverseFollow.setSender(userRepository.findById(receiverId).orElse(null));
            reverseFollow.setReceiver(userRepository.findById(senderId).orElse(null));
            reverseFollow.setState(FriendRequestStatus.ACCEPT);
            followRepository.save(reverseFollow); // B -> A 관계
        } else {
            throw new IllegalArgumentException("Friend request not found or already accepted");
        }
    }

    public void rejectFriendRequest(String senderId, String receiverId) {
        Optional<Follow> followOpt = followRepository.findBySenderAndReceiverAndState(
                userRepository.findById(senderId).orElse(null),
                userRepository.findById(receiverId).orElse(null),
                FriendRequestStatus.RECEIVE);

        followOpt.ifPresent(follow -> {
            follow.setState(FriendRequestStatus.DELETE);
            followRepository.save(follow);
        });
    }

    public void deleteFriend(String senderId, String receiverId) {
        // A -> B 관계 삭제
        Optional<Follow> followOpt1 = followRepository.findBySenderAndReceiverAndState(
                userRepository.findById(senderId).orElse(null),
                userRepository.findById(receiverId).orElse(null),
                FriendRequestStatus.ACCEPT);

        followOpt1.ifPresent(follow -> {
            follow.setState(FriendRequestStatus.DELETE);
            followRepository.save(follow);
        });

        // B -> A 관계 삭제
        Optional<Follow> followOpt2 = followRepository.findBySenderAndReceiverAndState(
                userRepository.findById(receiverId).orElse(null),
                userRepository.findById(senderId).orElse(null),
                FriendRequestStatus.ACCEPT);

        followOpt2.ifPresent(follow -> {
            follow.setState(FriendRequestStatus.DELETE);
            followRepository.save(follow);
        });
    }
}
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

    public Follow acceptFriendRequest(String senderId, String receiverId) {
        Optional<Follow> followOpt = followRepository.findBySenderAndReceiverAndState(
                userRepository.findById(senderId).orElse(null),
                userRepository.findById(receiverId).orElse(null),
                FriendRequestStatus.RECEIVE);

        if (followOpt.isPresent()) {
            Follow follow = followOpt.get();
            follow.setState(FriendRequestStatus.ACCEPT);
            return followRepository.save(follow);
        }
        throw new IllegalArgumentException("Friend request not found or already accepted");
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
        Optional<Follow> followOpt = followRepository.findBySenderAndReceiverAndState(
                userRepository.findById(senderId).orElse(null),
                userRepository.findById(receiverId).orElse(null),
                FriendRequestStatus.ACCEPT);

        followOpt.ifPresent(follow -> {
            follow.setState(FriendRequestStatus.DELETE);
            followRepository.save(follow);
        });
    }
}
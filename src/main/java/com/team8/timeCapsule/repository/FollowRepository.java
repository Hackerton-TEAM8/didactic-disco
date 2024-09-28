package com.team8.timeCapsule.repository;

import com.team8.timeCapsule.domain.Follow;
import com.team8.timeCapsule.domain.UserEntity;
import com.team8.timeCapsule.domain.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    // 승인 상태인 친구를 조회하는 메서드
    List<Follow> findBySenderOrReceiverAndState(UserEntity sender, UserEntity receiver, FriendRequestStatus status);

    // 특정 상태를 가지는 친구 요청을 조회하는 메서드
    Optional<Follow> findBySenderAndReceiverAndState(UserEntity sender, UserEntity receiver, FriendRequestStatus status);

    // 발신자로 검색하는 메서드 (특정 사용자가 보낸 친구 요청 조회)
    List<Follow> findBySender(UserEntity sender);

    // 수신자로 검색하는 메서드 (특정 사용자가 받은 친구 요청 조회)
    List<Follow> findByReceiver(UserEntity receiver);

    // 특정 상태를 제외한 모든 요청 조회 (친구 삭제나 거절 시 사용 가능)
    List<Follow> findBySenderAndReceiverAndStateNot(UserEntity sender, UserEntity receiver, FriendRequestStatus status);
}
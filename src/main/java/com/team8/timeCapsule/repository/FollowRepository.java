package com.team8.timeCapsule.repository;

import com.team8.timeCapsule.domain.Follow;
import com.team8.timeCapsule.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.team8.timeCapsule.domain.FriendRequestStatus;

public interface FollowRepository extends JpaRepository<Follow, String> {
    List<Follow> findBySenderOrReceiverAndState(UserEntity sender, UserEntity receiver, FriendRequestStatus status);
}
package com.team8.timeCapsule.domain;

public enum FriendRequestStatus {
    RECEIVE(0), // 0: 요청 수신
    ACCEPT(1),  // 1: 요청 수락
    DELETE(2);  // 2: 요청 삭제

    private final int value;

    FriendRequestStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FriendRequestStatus fromValue(int value) {
        for (FriendRequestStatus status : FriendRequestStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

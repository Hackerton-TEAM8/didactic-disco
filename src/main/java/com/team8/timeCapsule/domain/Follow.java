package com.team8.timeCapsule.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private FriendRequestStatus state;

    // senderId와 receiverId를 반환하는 메서드 추가
    public String getSenderId() {
        return sender != null ? sender.getId() : null;
    }

    public String getReceiverId() {
        return receiver != null ? receiver.getId() : null;
    }
}
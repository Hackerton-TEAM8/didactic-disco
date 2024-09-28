package com.team8.timeCapsule.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
public class TimeCapsule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeCapsuleId;

    @ManyToOne // Many-to-One 관계 설정
    @JoinColumn(name = "user_id", nullable = false) // 외래 키 설정
    private UserEntity user;

    private String imageUrl;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDateTime createDate;

    private Boolean isActive;

    private LocalDateTime unlockDate;

    private String username; // 소유자 이름 추가
    private String profileImageUrl; // 소유자 프로필 이미지 URL 추가
}

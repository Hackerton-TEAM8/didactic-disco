package com.team8.timeCapsule.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Boolean isConfirm;

    @OneToOne
    @JoinColumn(name = "time_capsule_id", referencedColumnName = "timeCapsuleId")
    private TimeCapsule timeCapsule;

    @Column(name = "unlock_date")
    private LocalDateTime unlockDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user; // 유저와의 관계 추가
}

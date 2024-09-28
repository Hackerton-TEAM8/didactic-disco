package com.team8.timeCapsule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Boolean isConfirm;
    private Long timeCapsuleId;
    @Column(name = "unlock_date")
    private LocalDateTime unlockDate;

    @Column(name = "user_id")
    private Long userId; // 알람을 받을 사용자 ID

}
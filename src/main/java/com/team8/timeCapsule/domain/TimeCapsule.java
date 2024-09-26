package com.team8.timeCapsule.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TimeCapsule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeCapsuleId;

    private Long userId;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDateTime createDate;

    private Boolean isActive;

    private LocalDateTime unlockDate;

}
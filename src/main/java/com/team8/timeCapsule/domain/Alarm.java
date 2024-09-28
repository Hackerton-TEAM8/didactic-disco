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

    @OneToOne
    @JoinColumn(name = "time_capsule_id", referencedColumnName = "timeCapsuleId")
    private TimeCapsule timeCapsule;

    @Column(name = "unlock_date")
    private LocalDateTime unlockDate;

    @Column(name = "user_id")
    private Long userId;
}

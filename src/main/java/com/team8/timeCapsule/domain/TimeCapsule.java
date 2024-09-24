package com.team8.timeCapsule.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class TimeCapsule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer capsuleID;

    @OneToOne
    @JoinColumn(name = "contentID", nullable = false)
    private Content content;

    private LocalDate unlockDate;

    private Boolean isActive;

}
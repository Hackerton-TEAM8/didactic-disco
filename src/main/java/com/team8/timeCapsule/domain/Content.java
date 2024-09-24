package com.team8.timeCapsule.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String picture;

    private String text;

    private LocalDate createDate;

    private Boolean isTimeCapsule;

    @OneToOne(mappedBy = "content", cascade = CascadeType.ALL)
    private TimeCapsule timeCapsule;

}
package com.team8.timeCapsule.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String password;

    private String username;

    @OneToMany(mappedBy = "user")
    private List<Content> contents;


}

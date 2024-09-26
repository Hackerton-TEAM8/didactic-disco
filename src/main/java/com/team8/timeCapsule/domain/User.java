package com.team8.timeCapsule.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String password;

    private String username;


}

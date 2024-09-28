package com.team8.timeCapsule.controller;

import com.team8.timeCapsule.domain.UserEntity;
import com.team8.timeCapsule.dto.UserDTO;
import com.team8.timeCapsule.security.TokenProvider;
import com.team8.timeCapsule.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // UserEntity 생성
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();

            // 유저 등록
            UserEntity registeredUser = userService.create(user);
            // 등록된 유저 정보를 담아 반환할 DTO 생성
            UserDTO responseUserDTO = UserDTO.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO); // 200 OK
        } catch (Exception e) {
            // 로그에 에러 메시지 출력
            log.error("User registration failed: {}", e.getMessage());
            // 에러 응답 반환
            return ResponseEntity.badRequest().body("User registration failed: " + e.getMessage()); // 400 Bad Request
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        try {
            // 유저 인증
            UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword(), passwordEncoder);

            if (user != null) {
                // 토큰 생성 및 반환
                final String token = tokenProvider.create(user);
                final UserDTO responseUserDTO = UserDTO.builder()
                        .email(user.getEmail())
                        .id(user.getId())
                        .token(token)
                        .build();

                return ResponseEntity.ok().body(responseUserDTO); // 200 OK
            } else {
                // 로그인 실패 시 로그 출력
                log.error("Login failed for user: {}", userDTO.getEmail());
                // 에러 응답 반환
                return ResponseEntity.badRequest().body("Login failed: invalid credentials"); // 400 Bad Request
            }
        } catch (Exception e) {
            // 로그에 에러 메시지 출력
            log.error("Authentication failed: {}", e.getMessage());
            // 에러 응답 반환
            return ResponseEntity.badRequest().body("Authentication failed: " + e.getMessage()); // 400 Bad Request
        }
    }
}
package com.team8.timeCapsule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // H2 콘솔 사용을 위한 프레임 옵션 비활성화
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/h2-console/**").permitAll()  // H2 콘솔 접근 허용
                        .anyRequest().authenticated()  // 나머지 요청은 인증 필요
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")  // H2 콘솔은 CSRF 보호 비활성화
                )
                .formLogin()
                .and()
                .httpBasic();  // 기본 인증 방식 사용

        return http.build();
    }

    // 비밀번호 암호화를 위한 PasswordEncoder 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

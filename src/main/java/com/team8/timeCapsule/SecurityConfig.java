//package com.team8.timeCapsule;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // H2 콘솔 사용을 위한 프레임 옵션 비활성화
//                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll() // 모든 요청을 허용
//                        .requestMatchers(HttpMethod.POST, "/**").permitAll() // POST 요청을 허용
//                        .anyRequest().permitAll()) // 나머지 요청에 대해서는 인증 필요
//                .csrf(csrf -> csrf.disable())  // CSRF 보호 완전히 비활성화
//                .formLogin()
//                .and()
//                .httpBasic();  // 기본 인증 방식 사용
//
//        return http.build();
//    }
//
//    // 비밀번호 암호화를 위한 PasswordEncoder 빈
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

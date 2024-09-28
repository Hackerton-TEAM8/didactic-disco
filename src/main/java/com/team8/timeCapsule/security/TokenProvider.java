package com.team8.timeCapsule.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.team8.timeCapsule.domain.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "YourVeryLongSecretKeyThatIsAtLeast64BytesLongToSatisfyHS512AlgorithmRequirements!";

    private final Key key;

    public TokenProvider() {
        // 시크릿 키를 이용하여 Key 객체를 생성합니다
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String create(UserEntity userEntity) {
        Date expireDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(userEntity.getId())
                .setIssuer("timecapsule")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }


    // 요청에서 토큰 추출 메서드 (컨트롤러와 동일)
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}

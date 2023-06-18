package com.likelion.config.jwt;

import com.likelion.config.JwtProperties;
import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user) {
        Date now = new Date();
        // TODO: SecretKey 못 받는 에러 잡아야 됨. -> test 전용 yml에 추가를 안 해서 발생한 에러...!
        System.out.println("jwtProperties = " + jwtProperties.getIssuer());
        System.out.println("jwtProperties = " + jwtProperties.getSecretKey());
        log.info("userRole check = {} ", user.getRole());
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
                .setIssuer(jwtProperties.getIssuer()) // 내용 iss : properties 파일에서 설정한 값
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변수값
                .setSubject(user.getUsername()) // 내용 sub : 유저 계정
                .claim("id", user.getId()) // 클레임 id : 유저 ID
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
                    return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * makeToken() 메서드에서 claim("role") 권한을 넣어놓고,
     * getClaims() 메서드를 이용해서 유저 권한을 빼자.
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + claims.get("role")));

//        return new UsernamePasswordAuthenticationToken(
//                new org.springframework.security.core.userdetails.User(
//                        "테스트", "", authorities), token, authorities);
        return new UsernamePasswordAuthenticationToken(claims.get("id"), "gd", authorities);

    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }


    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}

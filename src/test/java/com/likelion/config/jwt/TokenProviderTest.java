package com.likelion.config.jwt;

import com.likelion.config.JwtProperties;
import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import com.likelion.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProperties jwtProperties;

    @DisplayName("generateToken 검증, 유저 정보와 만료 기간을 전달해 토큰 생성 가능하다")
    @Test
    void generateToken() {
        // given
        User user = userRepository.save(User.builder()
                .username("user")
                .password("1234")
                .role(UserRole.USER)
                .build());

        // when
        String token = tokenProvider.generateToken(user, Duration.ofDays(14));

        // then
        Long userId = tokenProvider.getUserId(token);
        assertThat(user.getId()).isEqualTo(userId);

    }

    @DisplayName("validToken 검증, 만료된 토큰이면 유효성 검증에 실패")
    @Test
    void validToken_invalidToken() {
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .subject("gd")
                .build()
                .createToken(jwtProperties);


        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰일 때 유효성 검증에 성공")
    @Test
    void validToken_validToken() {
        // given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }

    // TODO 메서드 리팩토링 하면서 이 로직으로 진행 불가능함.
//    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 갖고 옴")
//    @Test
//    void getAuthentication() {
//        // given
//        String username = "bang";
//        String token = JwtFactory.builder()
//                .subject(username)
//
//                .build()
//                .createToken(jwtProperties);
//
//        // when
//        Authentication authentication = tokenProvider.getAuthentication(token);
//        System.out.println("authentication = " + authentication);
//        // then
////        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(username);
//        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(1L);
//    }

    @DisplayName("getUserId(): 토큰으로 유저 ID 갖고 옴")
    @Test
    void getUserId() {
        // given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // when
        Long getUserId = tokenProvider.getUserId(token);

        // then
        assertThat(getUserId).isEqualTo(userId);
    }
}
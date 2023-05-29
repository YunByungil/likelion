package com.likelion.domain.repository;

import com.likelion.config.JwtProperties;
import com.likelion.config.jwt.JwtFactory;
import com.likelion.domain.entity.RefreshToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefreshTokenRepositoryTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    JwtProperties jwtProperties;

    @AfterEach
    public void end() {
        refreshTokenRepository.deleteAll();
    }

    @DisplayName("리프레쉬 토큰 생성 및 조회 테스트")
    @Test
    void createAndFind() {

        // given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.builder()
                .userId(1L)
                .refreshToken(token)
                .build());

        // when
        RefreshToken findToken = refreshTokenRepository.findByRefreshToken(token).get();
        RefreshToken findToken2 = refreshTokenRepository.findById(1L).get();

        // then
        assertThat(findToken.getRefreshToken()).isEqualTo(token);
        assertThat(findToken2.getRefreshToken()).isEqualTo(token);
    }

}
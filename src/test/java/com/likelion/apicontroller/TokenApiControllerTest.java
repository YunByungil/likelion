package com.likelion.apicontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.config.JwtProperties;
import com.likelion.config.jwt.JwtFactory;
import com.likelion.domain.entity.RefreshToken;
import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import com.likelion.domain.repository.RefreshTokenRepository;
import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.token.CreateAccessTokenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class})
class TokenApiControllerTest {

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("createNewAccessToken: 새로운 액세스 토큰 발급")
    @Test
    void createNewAccessToken() throws Exception {
        // given
        String url = "http://localhost:8080/api/v1/token";

        User user = userRepository.save(User.builder()
                .username("test")
                .password("1234")
                .role(UserRole.USER)
                .build());

        String refreshToken = JwtFactory.builder()
                .claims(Map.of("id", user.getId()))
                .build()
                .createToken(jwtProperties);

        refreshTokenRepository.save(RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build());

        CreateAccessTokenRequest request = CreateAccessTokenRequest.builder()
                .refreshToken(refreshToken)
                .build();

        // when
        ResultActions resultActions = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request.getRefreshToken())));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

}
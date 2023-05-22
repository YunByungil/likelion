package com.likelion.apicontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.user.UserJoinRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("회원가입 완료_Mock")
    @Test
    void join() throws Exception {
        // given
        String userName = "bang12";
        String password = "1234";
        UserRole role = UserRole.USER;

        UserJoinRequestDto requestDto = UserJoinRequestDto.builder()
                .userName(userName)
                .password(password)
                .role(role)
                .build();

        String url = "http://localhost:8080/api/v1/join";

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        // then
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getUserName()).isEqualTo(userName);
        assertThat(all.get(0).getPassword()).isEqualTo(password);
    }
}
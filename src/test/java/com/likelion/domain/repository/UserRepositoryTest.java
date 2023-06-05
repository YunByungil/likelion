package com.likelion.domain.repository;

import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }
    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @DisplayName("회원가입 후 회원 조회")
    @Test
    void join() {
        // given
        String email = "bang12@naver.com";
        String password = "1234";
        String nickname = "nickname";
        UserRole userRole = UserRole.USER;

        userRepository.save(User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(userRole)
                .build());

        // when
        List<User> userList = userRepository.findAll();

        // then
        User user = userList.get(0);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @DisplayName("findByUsername 메서드 테스트")
    @Test
    void findByUsername() {
        // given
        String email = "bang12@naver.com";
        String password = "1234";
        UserRole userRole = UserRole.USER;

        userRepository.save(User.builder()
                .email(email)
                .password(password)
                .role(userRole)
                .build());

        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getEmail()).isEqualTo(email);
        // when
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // then
        assertThat(user.getEmail()).isEqualTo(email);
    }

}
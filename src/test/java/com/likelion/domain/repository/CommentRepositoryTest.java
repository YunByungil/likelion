package com.likelion.domain.repository;

import com.likelion.domain.entity.Comment;
import com.likelion.domain.entity.Post;
import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void end() {
        commentRepository.deleteAll();
    }

    @DisplayName("댓글 작성 테스트")
    @Test
    void addComment() {
        // given
        User user = userRepository.save(User.builder()
                .email("test@naver.com")
                .password("gd")
                .role(UserRole.USER)
                .username("테스터")
                .nickname("tester")
                .build());

        Post post = postRepository.save(Post.builder()
                .title("제목")
                .content("내용")
                .author(user.getUsername())
                .user(user)
                .build());

        Comment comment = commentRepository.save(Comment.builder()
                .content("댓글 내용입니다.")
                .post(post)
                .user(user)
                .build());
        // when
        List<Comment> all = commentRepository.findAll();

        // then
        assertThat(all.size()).isEqualTo(1);
    }
}
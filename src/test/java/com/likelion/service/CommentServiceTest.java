package com.likelion.service;

import com.likelion.domain.entity.Comment;
import com.likelion.domain.entity.Post;
import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import com.likelion.domain.repository.CommentRepository;
import com.likelion.domain.repository.PostRepository;
import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.comment.CommentSaveRequestDto;
import com.likelion.dto.comment.CommentUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    Post post;
    @BeforeEach
    public void setUp() {
        commentRepository.deleteAll();

        user = userRepository.save(User.builder()
                .nickname("닉네임")
                .username("작성자")
                .role(UserRole.USER)
                .email("test@naver.com")
                .password("gd")
                .build());

        post = postRepository.save(Post.builder()
                .user(user)
                .author(user.getUsername())
                .content("내용")
                .title("제목")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), user.getAuthorities()));
    }

    @AfterEach
    public void end() {
        commentRepository.deleteAll();
    }

    @DisplayName("댓글 작성 후 조회 테스트")
    @Test
    void saveCommentAndGet() {
        // given
        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .content("댓글 내용")
                .build();

        commentRepository.save(dto.toEntity(user, post));

        // when
        List<Comment> all = commentRepository.findAll();

        // then
        assertThat(all.size()).isEqualTo(1);
    }

    /**
     * 다른 사람 댓글 삭제할 때 예외 발생 테스트 추가 작성
     */
    @DisplayName("댓글 삭제 기능 테스트 (검증 포함)")
    @Test
    void deleteComment() {
        // given
        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .content("댓글 내용")
                .build();

        Comment comment = commentRepository.save(dto.toEntity(user, post));

        // when
        commentService.delete(user.getId(), post.getId(), comment.getId());

        List<Comment> all = commentRepository.findAll();

        // then
        assertThat(all.size()).isEqualTo(0);
    }

    @DisplayName("내가 작성한 댓글이 아닐 때 예외(권한) 발생")
    @Test
    void throwException() {
        // given

        User user2 = userRepository.save(User.builder()
                .nickname("닉네임")
                .username("작성자")
                .role(UserRole.USER)
                .email("test@naver.com")
                .password("gd")
                .build());

        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .content("댓글 내용")
                .build();

        Comment comment = commentRepository.save(dto.toEntity(user, post));
        // when

        // then
        assertThatThrownBy(() -> {
            commentService.delete(user2.getId(), post.getId(), comment.getId());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("내가 작성한 댓글이 아닐 때 수정하면 예외 발생")
    @Test
    void 내가_작성한_댓글이_아니면_예외() {
        // given
        User user2 = userRepository.save(User.builder()
                .nickname("닉네임")
                .username("작성자")
                .role(UserRole.USER)
                .email("test@naver.com")
                .password("gd")
                .build());

        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .content("댓글 내용")
                .build();

        CommentUpdateRequestDto updateDto = CommentUpdateRequestDto.builder()
                .content("수정 내용")
                .build();

        Comment comment = commentRepository.save(dto.toEntity(user, post));

        // then
        assertThatThrownBy(() -> {
            commentService.update(user2.getId(), post.getId(), comment.getId(), updateDto);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("댓글 수정 기능 테스트 (검증 포함)")
    @Test
    void update() {
        // given
        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .content("댓글 내용")
                .build();

        CommentUpdateRequestDto updateDto = CommentUpdateRequestDto.builder()
                .content("수정 내용")
                .build();

        Comment comment = commentRepository.save(dto.toEntity(user, post));

        // when
        commentService.update(user.getId(), post.getId(), comment.getId(), updateDto);

        List<Comment> all = commentRepository.findAll();

        // then
        assertThat(all.get(0).getContent()).isEqualTo(updateDto.getContent());
    }
}
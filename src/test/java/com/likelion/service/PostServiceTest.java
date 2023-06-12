package com.likelion.service;

import com.likelion.domain.entity.Post;
import com.likelion.domain.entity.User;
import com.likelion.domain.repository.PostRepository;
import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.post.PostListResponseDto;
import com.likelion.dto.post.PostSaveRequestDto;
import com.likelion.dto.post.PostUpdateRequestDto;
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
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User user;
    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();

        /* 로그인 정보때문에 필요함 */
        user = userRepository.save(User.builder()
                .email("user@gamil.com")
                .password("test")
                .username("작성자")
                .nickname("닉네임")
                .build());

        SecurityContext context1 = SecurityContextHolder.getContext();
        context1.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }
    @AfterEach
    public void end() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 전체 조회 테스트")
    @Test
    void getAllPost() {
        // given
        String author = "닉네임";
        String title = "게시글";
        String content = "내용";

        for (int i = 0; i < 5; i++) {
            PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                    .title(title + i)
                    .content(content)
                    .build();
            postService.save(requestDto, author);
        }


        // when
        List<PostListResponseDto> all = postService.findAll();

        // then
        assertThat(all.size()).isEqualTo(5);
        assertThat(all.get(0).getTitle()).isEqualTo(title + 0);
        assertThat(all.get(1).getTitle()).isEqualTo(title + 1);
    }

    @DisplayName("게시글 삭제 테스트")
    @Test
    void deletePost() {
        // given
        String title = "게시글";
        String content = "내용";

        PostSaveRequestDto saveRequestDto = PostSaveRequestDto.builder()
                .author(user.getUsername())
                .title(title)
                .content(content)
                .build();

        Post post = postService.save(saveRequestDto, user.getUsername());

        // when
        postService.deletePost(post.getId()); // Service에서 getContext가 아닌, Controller에서 체크하는 식으로 진행한다.

        List<Post> all = postRepository.findAll();

        // then
        assertThat(all.size()).isEqualTo(0);
    }

    @DisplayName("게시글 수정 테스트")
    @Test
    void updatePost() {
        // given
        String title = "게시글";
        String content = "내용";

        Post post = postService.save(PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build(), user.getUsername());

        String changeTitle = "게시글수정";
        String changeContent = "내용수정";
        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(changeTitle)
                .content(changeContent)
                .build();

        // when
        postService.updatePost(post.getId(), requestDto);

        // then
        List<PostListResponseDto> all = postService.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(changeTitle);
        assertThat(all.get(0).getContent()).isEqualTo(changeContent);

    }
}
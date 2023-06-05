package com.likelion.service;

import com.likelion.domain.entity.Post;
import com.likelion.domain.repository.PostRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
    }
    @AfterEach
    public void end() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 전체 조회 테스트")
    @Test
    void getAllPost() {
        // given
        String author = "작성자";
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
        String author = "작성자";
        String title = "게시글";
        String content = "내용";

        PostSaveRequestDto saveRequestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        Post post = postService.save(saveRequestDto, author);

        // when
        postService.deletePost(post.getId());

        List<Post> all = postRepository.findAll();

        // then
        assertThat(all.size()).isEqualTo(0);
    }

    @DisplayName("게시글 수정 테스트")
    @Test
    void updatePost() {
        // given
        String author = "작성자";
        String title = "게시글";
        String content = "내용";

        Post post = postService.save(PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build(), author);

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
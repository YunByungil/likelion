package com.likelion.service;

import com.likelion.domain.entity.Post;
import com.likelion.domain.repository.PostRepository;
import com.likelion.dto.post.PostSaveRequestDto;
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
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void end() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 전체 조회 테스트")
    @Test
    void getAllPost() {
        // given
        String title = "게시글";
        String content = "내용";

        for (int i = 0; i < 5; i++) {
            PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                    .title(title + i)
                    .content(content)
                    .build();
            postService.save(requestDto);
        }


        // when
        List<Post> all = postService.findAll();

        // then
        assertThat(all.size()).isEqualTo(5);
        assertThat(all.get(0).getTitle()).isEqualTo(title + 0);
        assertThat(all.get(1).getTitle()).isEqualTo(title + 1);
    }
}
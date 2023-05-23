package com.likelion.domain.repository;

import com.likelion.domain.entity.Post;
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
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void end() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 저장 불러오기")
    @Test
    void addPost() {
        // given
        String title = "제목1";
        String content = "내용1";

        Post save = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        // when
        List<Post> savedPost = postRepository.findAll();

        // then
        assertThat(savedPost.get(0).getTitle()).isEqualTo(title);
        assertThat(savedPost.get(0).getContent()).isEqualTo(content);
    }
}
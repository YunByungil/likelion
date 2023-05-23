package com.likelion.apicontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.domain.entity.Post;
import com.likelion.domain.repository.PostRepository;
import com.likelion.dto.post.PostSaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PostApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        postRepository.deleteAll();
    }

    @AfterEach
    public void end() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 작성 성공")
    @Test
    void addPost() throws Exception {
        // given
        String title = "제목";
        String content = "내용";

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();


        String url = "http://localhost:8080/api/v1/post";

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated());

        // then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("게시글 목록 조회 성공")
    @Test
    void getAllPost() throws Exception {
        // given
        String title = "제목임";
        String content = "내용임";
        String url = "http://localhost:8080/api/v1/post";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        // when
        mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));

        // then
    }

    @DisplayName("게시글 상세 조회")
    @Test
    void getPost() throws Exception {
        // given
        String title = "상세제목";
        String content = "상세내용";

        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        String url = "http://localhost:8080/api/v1/post/" + post.getId();

        // when
//        mvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value(title))
//                .andExpect(jsonPath("$.content").value(content));
        ResultActions resultActions = mvc.perform(get(url).accept(MediaType.APPLICATION_JSON));


        // then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));


    }
}
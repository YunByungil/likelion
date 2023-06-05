package com.likelion.apicontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.domain.entity.Post;
import com.likelion.domain.entity.User;
import com.likelion.domain.repository.PostRepository;
import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.post.PostSaveRequestDto;
import com.likelion.dto.post.PostUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest
//@AutoConfigureRestDocs
class PostApiControllerTest {

    @Autowired
    private WebApplicationContext context;


    private MockMvc mvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        postRepository.deleteAll();

        user = userRepository.save(User.builder()
                .email("user@gamil.com")
                .password("test")
                .build());

        SecurityContext context1 = SecurityContextHolder.getContext();
        context1.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    @AfterEach
    public void end() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 작성 성공")
    @Test
    void addPost() throws Exception {
        // given
        String author = "작성자";
        String title = "제목";
        String content = "내용";

        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();


        String url = "http://localhost:8080/api/v1/post";

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("작성자");

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(principal)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andDo(document("/post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("Post 제목"),
                                fieldWithPath("content").description("Post 내용"),
                                fieldWithPath("author").description("Post 작성자")
                        )
                ));

        // then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("게시글 목록 조회 성공")
    @Test
    void getAllPost() throws Exception {
        // given
        String author = "작성자";
        String title = "제목임";
        String content = "내용임";
        String url = "http://localhost:8080/api/v1/post";

        for (int i = 0; i < 5; i++) {
            postRepository.save(Post.builder()
                    .author(author + i)
                    .title(title + i)
                    .content(content + i)
                    .build());
        }
//        Post post = postRepository.findById(1L).get();

        // when
        mvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1))//
                .andExpect(jsonPath("$[0].content").value(content + 0))
                .andExpect(jsonPath("$[0].title").value(title + 0))
//                .andExpect(jsonPath("$[0].createAt").value(post.getCreatedAt()))
                .andDo(document("/post-get-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").description("Post 번호"),
                                fieldWithPath("[].title").description("Post 제목"),
                                fieldWithPath("[].content").description("Post 내용"),
                                fieldWithPath("[].author").description("Post 작성자"),
                                fieldWithPath("[].createdAt").description("Post 작성일")
                        )));

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

    @DisplayName("게시글 삭제")
    @Test
    void deletePost() throws Exception {
        // given
        String title = "제목1";
        String content = "내용1";

        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        String url = "http://localhost:8080/api/v1/post/" + post.getId();

        // when
        mvc.perform(delete(url))
                .andExpect(status().isOk());

        // then
        List<Post> all = postRepository.findAll();

        assertThat(all).isEmpty();
    }

    @DisplayName("게시글 수정")
    @Test
    void updatePost() throws Exception {
        // given
        String title = "제목";
        String content = "내용";

        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        String newTitle = "새로운 제목";
        String newContent = "새로운 내용";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(newTitle)
                .content(newContent)
                .build();

        String url = "http://localhost:8080/api/v1/post/" + post.getId();

        // when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Post> all = postRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(newTitle);
        assertThat(all.get(0).getContent()).isEqualTo(newContent);
    }
}
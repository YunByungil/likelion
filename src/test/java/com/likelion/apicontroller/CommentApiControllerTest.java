package com.likelion.apicontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.domain.entity.Comment;
import com.likelion.domain.entity.Post;
import com.likelion.domain.entity.User;
import com.likelion.domain.repository.CommentRepository;
import com.likelion.domain.repository.PostRepository;
import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.comment.CommentSaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@SpringBootTest
class CommentApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    User user;
    Post post;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        commentRepository.deleteAll();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withResponseDefaults(prettyPrint())
                .withRequestDefaults(prettyPrint()))
                .build();

        user = userRepository.save(User.builder()
                .email("user@gamil.com")
                .password("test")
                .username("작성자")
                .nickname("닉네임")
                .build());

        SecurityContext context1 = SecurityContextHolder.getContext();
        context1.setAuthentication(new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), user.getAuthorities()));


        post = postRepository.save(Post.builder()
                .user(user)
                .author(user.getUsername())
                .content("내용")
                .title("제목")
                .build());
    }

    @AfterEach
    public void end() {
        commentRepository.deleteAll();
    }

    @DisplayName("댓글 작성 성공")
    @Test
    void saveComment() throws Exception {
        // given
        String content = "댓글 작성";
        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .content(content)
                .build();

        String url = "http://localhost:8080/api/v1/post/" + post.getId() + "/comment";

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("" + user.getId());

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(principal)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(document("/comment",
                        requestFields(
                                fieldWithPath("content").description("Comment 내용")
                        )
                ));

        // then
        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }
}
package com.likelion.apicontroller;

import com.likelion.domain.entity.Post;
import com.likelion.dto.post.PostResponseDto;
import com.likelion.dto.post.PostSaveRequestDto;
import com.likelion.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/post")
    public ResponseEntity<Post> addPost(@RequestBody PostSaveRequestDto requestDto) {
        Post savedPost = postService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPost);
    }

    @GetMapping("/api/v1/post")
    public ResponseEntity<List<PostResponseDto>> getAllPost() {
        List<PostResponseDto> postList = postService.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(postList);
    }

}

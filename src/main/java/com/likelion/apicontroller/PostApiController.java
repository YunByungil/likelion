package com.likelion.apicontroller;

import com.likelion.domain.entity.Post;
import com.likelion.dto.post.PostListResponseDto;
import com.likelion.dto.post.PostResponseDto;
import com.likelion.dto.post.PostSaveRequestDto;
import com.likelion.dto.post.PostUpdateRequestDto;
import com.likelion.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/post")
    public ResponseEntity<Void> addPost(@RequestBody PostSaveRequestDto requestDto,
                                        Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        postService.save(requestDto, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(savedPost);
    }

    @GetMapping("/api/v1/post")
    public ResponseEntity<List<PostListResponseDto>> getAllPost() {
        List<PostListResponseDto> postList = postService.findAll();

        return ResponseEntity.ok().body(postList);
    }

    @GetMapping("/api/v1/post/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        PostResponseDto responseDto = postService.findById(id);

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/api/v1/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/post/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id,
                                           @RequestBody PostUpdateRequestDto requestDto) {
        postService.updatePost(id, requestDto);

        return ResponseEntity.ok().build();
    }

}

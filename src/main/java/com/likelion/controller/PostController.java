package com.likelion.controller;

import com.likelion.domain.entity.Post;
import com.likelion.dto.post.PostListResponseDto;
import com.likelion.dto.post.PostResponseDto;
import com.likelion.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public String getPostList(Model model) {
        List<Post> all = postService.findAll();
        List<PostListResponseDto> postList = all.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());

        model.addAttribute("postList", postList);
        return "postList";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        PostResponseDto post = postService.findById(id);
        model.addAttribute("post", post);

        return "post";
    }
}

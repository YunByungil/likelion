package com.likelion.controller;

import com.likelion.domain.entity.Post;
import com.likelion.dto.post.PostListResponseDto;
import com.likelion.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}

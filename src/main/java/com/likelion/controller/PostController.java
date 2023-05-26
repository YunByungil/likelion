package com.likelion.controller;

import com.likelion.domain.entity.Post;
import com.likelion.dto.post.PostListResponseDto;
import com.likelion.dto.post.PostResponseDto;
import com.likelion.service.PostService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Generated
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public String getPostList(Model model) {
        List<PostListResponseDto> postList = postService.findAll();

        model.addAttribute("postList", postList);
        return "postList";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        PostResponseDto post = postService.findById(id);
        model.addAttribute("post", post);

        return "post";
    }

    @GetMapping("/new-post")
    public String newPost(@RequestParam(required = false) Long id,
                          Model model) {
        if (id == null) {
            model.addAttribute("post", new PostResponseDto());
        } else {
            PostResponseDto post = postService.findById(id);
            model.addAttribute("post", post);
        }

        return "newPost";
    }
}

package com.likelion.dto.post;

import com.likelion.domain.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {

    private Long id;
    private String title;
    private String content;

    private LocalDateTime createdAt;

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}

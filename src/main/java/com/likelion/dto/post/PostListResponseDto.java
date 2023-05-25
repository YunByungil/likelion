package com.likelion.dto.post;

import com.likelion.domain.entity.Post;
import lombok.Getter;

@Getter
public class PostListResponseDto {

    private Long id;
    private String title;
    private String content;

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}

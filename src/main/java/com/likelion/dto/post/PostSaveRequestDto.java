package com.likelion.dto.post;

import com.likelion.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostSaveRequestDto {

    private String author;
    private String title;
    private String content;

    @Builder
    public PostSaveRequestDto(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public Post toEntity(String author) {
        return Post.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();
    }
}

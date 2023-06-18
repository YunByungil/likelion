package com.likelion.dto.post;

import com.likelion.domain.entity.Post;
import com.likelion.domain.entity.User;
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

    public Post toEntity(User user) {
        return Post.builder()
                .author(user.getUsername())
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}

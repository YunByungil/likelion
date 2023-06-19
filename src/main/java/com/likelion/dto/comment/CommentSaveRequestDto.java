package com.likelion.dto.comment;

import com.likelion.domain.entity.Comment;
import com.likelion.domain.entity.Post;
import com.likelion.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private User user;
    private Post post;
    private String content;
    private Comment comment;

    @Builder
    public CommentSaveRequestDto(String content) {
        this.content = content;
    }

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .parent(comment)
                .build();
    }
}

package com.likelion.dto.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateRequestDto {
    private String content;

    @Builder
    public CommentUpdateRequestDto(String content) {
        this.content = content;
    }

}

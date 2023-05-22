package com.likelion.dto.user;

import com.likelion.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateDto {

    private String password;

    @Builder
    public UserUpdateDto(String password) {
        this.password = password;
    }

}

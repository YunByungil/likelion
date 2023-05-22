package com.likelion.dto.user;

import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {

    private String userName;
    private String password;
    private UserRole role;

    @Builder
    public UserJoinRequestDto(String userName, String password, UserRole role) {
        this.userName = userName;
        this.password = password;
        this.role = UserRole.USER;
    }

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .password(password)
                .role(UserRole.USER)
                .build();
    }
}

package com.likelion.dto.user;

import com.likelion.domain.entity.User;
import com.likelion.domain.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserJoinRequestDto {

    private String email;
    private String username;
    private String nickname;
    private String password;
    private UserRole role;

    @Builder
    public UserJoinRequestDto(String email, String username, String nickname, String password, UserRole role) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = UserRole.USER;
    }

    public User toEntity(String password) {
        return User.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .password(password)
                .role(UserRole.USER)
                .build();
    }
}

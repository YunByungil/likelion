package com.likelion.dto.token;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateAccessTokenRequest {
    private String refreshToken;

    @Builder
    public CreateAccessTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}

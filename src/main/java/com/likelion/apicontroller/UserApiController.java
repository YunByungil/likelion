package com.likelion.apicontroller;

import com.likelion.dto.user.UserJoinRequestDto;
import com.likelion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/v1/join")
    public Long save(@RequestBody UserJoinRequestDto requestDto) {
        return userService.save(requestDto);
    }
}

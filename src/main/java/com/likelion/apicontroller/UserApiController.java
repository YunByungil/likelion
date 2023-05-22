package com.likelion.apicontroller;

import com.likelion.dto.user.UserJoinRequestDto;
import com.likelion.dto.user.UserUpdateDto;
import com.likelion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/v1/join")
    public Long save(@RequestBody UserJoinRequestDto requestDto) {
        return userService.save(requestDto);
    }

    @PutMapping("/api/v1/user/{id}")
    public Long updateUserInfo(@PathVariable Long id,
                               @RequestBody UserUpdateDto requestDto) {
        return userService.update(id, requestDto);
    }
}

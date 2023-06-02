package com.likelion.controller;

import com.likelion.dto.user.UserJoinRequestDto;
import com.likelion.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String join(UserJoinRequestDto requestDto) {
        log.info("requestDto = {}", requestDto.getEmail());
        userService.save(requestDto);
        return "redirect:/login";
    }

//    @GetMapping("/join")
//    public String join() {
//        return "join";
//    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        new SecurityContextLogoutHandler()
                .logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/login";
    }
}

package com.likelion.apicontroller;

import com.likelion.dto.comment.CommentSaveRequestDto;
import com.likelion.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/v1/post/{postId}/comment")
    public ResponseEntity save(@RequestBody CommentSaveRequestDto dto,
                               @PathVariable Long postId,
                               Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        commentService.save(userId, postId, dto);
        return ResponseEntity.ok().body(dto);
    }
}

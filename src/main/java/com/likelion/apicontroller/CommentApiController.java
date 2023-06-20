package com.likelion.apicontroller;

import com.likelion.dto.comment.CommentSaveRequestDto;
import com.likelion.dto.comment.CommentUpdateRequestDto;
import com.likelion.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/api/v1/post/{postId}/comment/{commentId}")
    public ResponseEntity delete(@PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        commentService.delete(userId, postId, commentId);
        return ResponseEntity.ok().body("댓글 삭제 완료");
    }

    @PutMapping("/api/v1/post/{postId}/comment/{commentId}")
    public ResponseEntity update(@PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 @RequestBody CommentUpdateRequestDto dto,
                                 Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        commentService.update(userId, postId, commentId, dto);
        return ResponseEntity.ok().body("댓글 수정 완료");
    }
}

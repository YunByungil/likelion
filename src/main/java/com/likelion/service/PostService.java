package com.likelion.service;

import com.likelion.domain.entity.Post;
import com.likelion.domain.entity.User;
import com.likelion.domain.repository.PostRepository;
import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.post.PostListResponseDto;
import com.likelion.dto.post.PostResponseDto;
import com.likelion.dto.post.PostSaveRequestDto;
import com.likelion.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post save(PostSaveRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        Post post = requestDto.toEntity(user);
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getId() = " + user.getId());
        System.out.println("post.getTitle() = " + post.getTitle());
        return postRepository.save(post);
    }

    // TODO: List<PostResponseDto>로 수정
    public List<PostListResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    // TODO: PostListResponse, PostResponse 두 개 나누기
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return new PostResponseDto(post);
    }

    public void deletePost(Long id, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        authorizePostAuthor(post, user);
        postRepository.delete(post);
    }

    public void updatePost(Long id, PostUpdateRequestDto requestDto, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        authorizePostAuthor(post, user);
        post.update(requestDto.getTitle(), requestDto.getContent());
    }

    private static void authorizePostAuthor(Post post, User user) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();

//        if (!post.getAuthor().equals(username)) {
//            throw new IllegalArgumentException("권한이 없습니다.");
//        }
        System.out.println("post.getUser().getId() = " + post.getUser().getId());
        if (post.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}

package com.likelion.service;

import com.likelion.domain.entity.Post;
import com.likelion.domain.repository.PostRepository;
import com.likelion.dto.post.PostResponseDto;
import com.likelion.dto.post.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Post save(PostSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity());
    }

    // TODO: List<PostResponseDto>로 수정
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // TODO: PostListResponse, PostResponse 두 개 나누기
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return new PostResponseDto(post);
    }
}

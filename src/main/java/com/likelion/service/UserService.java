package com.likelion.service;

import com.likelion.domain.entity.User;
import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.user.UserJoinRequestDto;
import com.likelion.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long save(UserJoinRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, UserUpdateDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        user.update(requestDto.getPassword());
        return id;
    }
}

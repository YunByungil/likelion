package com.likelion.service;

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

    public Long update(UserUpdateDto requestDto) {
        return null;
    }
}

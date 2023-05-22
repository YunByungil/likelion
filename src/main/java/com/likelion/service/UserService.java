package com.likelion.service;

import com.likelion.domain.repository.UserRepository;
import com.likelion.dto.user.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long save(UserJoinRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();
    }
}

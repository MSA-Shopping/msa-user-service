package com.example.msauserservice.domain.users;

import com.example.msauserservice.domain.users.dto.SignupRequestDto;
import com.example.msauserservice.global.exception.CustomException;
import com.example.msauserservice.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        // 저장하기 전 User 객체 생성
        User newUser = User.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .phoneNumber(requestDto.getPhoneNumber())
                .build();

        // User가 이미 존재하는지 확인
        if(userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            userRepository.save(newUser);
        } else {
            throw new CustomException(ErrorCode.CONFLICT_USER);
        }
    }
}

package com.example.msauserservice.domain.users;

import com.example.msauserservice.domain.users.dto.LoginRequestDto;
import com.example.msauserservice.domain.users.dto.LoginResponseDto;
import com.example.msauserservice.domain.users.dto.SignupRequestDto;
import com.example.msauserservice.global.UserRole;
import com.example.msauserservice.global.exception.CustomException;
import com.example.msauserservice.global.exception.ErrorCode;
import com.example.msauserservice.global.jwt.JwtUtil;
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
                .role(UserRole.USER)
                .build();

        // User가 이미 존재하는지 확인
        if(userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            userRepository.save(newUser);
        } else {
            throw new CustomException(ErrorCode.CONFLICT_USER);
        }
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        }

        String token = JwtUtil.createToken(user.getUsername(), JwtUtil.ACCESS_TOKEN_EXPIRATION);
        return new LoginResponseDto(token, "로그인 성공");
    }
}

package com.example.msauserservice.domain.users;

import com.example.msauserservice.domain.users.dto.SignupRequestDto;
import com.example.msauserservice.domain.users.dto.UserDto;
import com.example.msauserservice.global.UserRole;
import com.example.msauserservice.global.exception.CustomException;
import com.example.msauserservice.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        // 저장하기 전 User 객체 생성

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User newUser = User.builder()
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(encodedPassword)
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

    @Transactional(readOnly = true)
    public UserDto findEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().toString())
                .build();
    }
}

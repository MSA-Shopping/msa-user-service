package com.example.msauserservice.domain.users;

import com.example.msauserservice.domain.users.dto.LoginRequestDto;
import com.example.msauserservice.domain.users.dto.LoginResponseDto;
import com.example.msauserservice.domain.users.dto.SignupRequestDto;
import com.example.msauserservice.global.common.CommonResponse;
import com.example.msauserservice.global.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<CommonResponse> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        try {
            userService.signup(requestDto);
            CommonResponse response = new CommonResponse("회원가입 성공", 201, "");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            CommonResponse response = new CommonResponse("회원가입 실패", e.getStatusCode().value(), e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<CommonResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {
        try {
            LoginResponseDto responseDto = userService.login(requestDto);
            CommonResponse response = new CommonResponse("로그인 성공", 200, responseDto);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            CommonResponse response = new CommonResponse("로그인 실패", e.getStatusCode().value(), e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

package com.example.msauserservice.domain.users;

import com.example.msauserservice.domain.users.dto.EmailRequestDto;
import com.example.msauserservice.domain.users.dto.SignupRequestDto;
import com.example.msauserservice.domain.users.dto.UserDto;
import com.example.msauserservice.global.common.CommonResponse;
import com.example.msauserservice.global.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    // auth 에서 email 조회
    @PostMapping("/users/email")
    public UserDto findUserByEmail(@RequestBody EmailRequestDto requestDto) {
        try {
            return userService.findEmail(requestDto.getEmail());
        } catch (CustomException e) {
            throw e;
        }
    }

    // gateway를 통해 발급된 인증 객체로 인증해야 함
    @GetMapping("/users/needtoken")
    public ResponseEntity<CommonResponse> needToken(@AuthenticationPrincipal String userId) {
        CommonResponse response = new CommonResponse("토큰이 필요한 페이지", 200, userId);
        return ResponseEntity.ok(response);
    }

}

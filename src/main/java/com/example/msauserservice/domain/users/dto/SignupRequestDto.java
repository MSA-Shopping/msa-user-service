package com.example.msauserservice.domain.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "username 필수 입력 값입니다.")
    private String username;
    @NotBlank(message = "email 필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "password 필수 입력 값입니다.")
    private String password;
    @NotBlank(message = "phoneNumber 필수 입력 값입니다.")
    private String phoneNumber;

    @Builder
    public SignupRequestDto(String username, String email, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}

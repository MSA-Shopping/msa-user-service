package com.example.msauserservice.domain.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;

    @Builder
    public UserDto(Long id, String username, String email, String password, String phoneNumber, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}

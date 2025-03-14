package com.example.msauserservice.domain.users.dto;

import lombok.Getter;

@Getter
public class EmailRequestDto {
    String email;

    public EmailRequestDto(String email) {
        this.email = email;
    }
}

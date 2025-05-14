package com.ohgiraffers.midnight.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RequestLoginDto {
    private String email;
    private String password;
}
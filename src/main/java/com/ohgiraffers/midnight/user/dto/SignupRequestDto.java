package com.ohgiraffers.midnight.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    private String email;
    private String password;
    private String nickname;

}
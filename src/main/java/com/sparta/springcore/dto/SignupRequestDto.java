package com.sparta.springcore.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false;      //체크박스 부 false면 일반승인
    private String adminToken = "";     // 관리자 승인을 받기 위한 토큰 발행
}
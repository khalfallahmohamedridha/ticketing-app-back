package com.dksoft.tn.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;
    private UserDto user;

}
package com.dksoft.tn.controller;
import com.dksoft.tn.dto.*;
import com.dksoft.tn.service.AuthService;

import com.dksoft.tn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    UserService userService;

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){

        //01 - Receive the token from AuthService
        AuthResponseDto authResponseDto = authService.login(loginDto);

        //03 - Return the response to the user
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponseDto authResponseDto = authService.refreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public UserDto save(@RequestBody RegisterUserRequest userDto) {
        return userService.save(userDto);
    }
}
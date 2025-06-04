package com.dksoft.tn.service;

import com.dksoft.tn.configuration.JwtTokenProvider;
import com.dksoft.tn.dto.AuthResponseDto;
import com.dksoft.tn.dto.LoginDto;
import com.dksoft.tn.entity.User;
import com.dksoft.tn.mapper.UserMapper;
import com.dksoft.tn.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    private UserMapper userMapper;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       RedisTemplate<String, String> redisTemplate,
                       JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponseDto login(LoginDto loginDto) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        // Generate access token
        String accessToken = jwtTokenProvider.generateToken(authentication);

        // Generate refresh token
        String refreshToken = UUID.randomUUID().toString();

        // Store refresh token in Redis with expiration
        String username = authentication.getName();
        redisTemplate.opsForValue().set("refresh_token:" + username, refreshToken,
                604800000, TimeUnit.MILLISECONDS); // 7 days

        // Fetch user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Return both tokens
        AuthResponseDto response = new AuthResponseDto();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setUser(userMapper.fromUser(user));
        return response;
    }

    public AuthResponseDto refreshToken(String refreshToken) {
        // Find user associated with refresh token
        String username = redisTemplate.keys("refresh_token:*").stream()
                .filter(key -> refreshToken.equals(redisTemplate.opsForValue().get(key)))
                .map(key -> key.replace("refresh_token:", ""))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // Validate refresh token
        if (!redisTemplate.hasKey("refresh_token:" + username)) {
            throw new RuntimeException("Refresh token expired or invalid");
        }

        // Generate new access token
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String newAccessToken = jwtTokenProvider.generateToken(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        null,
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                                .collect(Collectors.toList())
                ));

        // Generate new refresh token
        String newRefreshToken = UUID.randomUUID().toString();

        // Replace old refresh token in Redis with new one
        redisTemplate.opsForValue().set("refresh_token:" + username, newRefreshToken,
                604800000, TimeUnit.MILLISECONDS); // 7 days

        // Return new access token and new refresh token
        AuthResponseDto response = new AuthResponseDto();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(newRefreshToken);
        return response;
    }
}
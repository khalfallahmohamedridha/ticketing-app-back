package com.dksoft.tn.dto;


import java.util.Set;

public record RegisterUserRequest(
        Long id ,
        String firstName ,
        String lastName ,
        String username ,
        String email ,
        String password ,
        String phone,
        Set<String> roles
) {}

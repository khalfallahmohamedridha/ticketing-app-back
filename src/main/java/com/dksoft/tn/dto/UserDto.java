package com.dksoft.tn.dto;

import com.dksoft.tn.entity.Role;

import java.util.Set;

public record UserDto(
        Long id ,
        String name ,
        String username ,
        String email ,
        String password ,
        Set<Role> roles
) {
}

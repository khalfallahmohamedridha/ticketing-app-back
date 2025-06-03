package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.RegisterUserRequest;
import com.dksoft.tn.entity.Role;
import org.springframework.stereotype.Service;

import com.dksoft.tn.dto.UserDto;
import com.dksoft.tn.entity.User;

import lombok.NonNull;

import java.util.stream.Collectors;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromRegisterUserRequest(@NonNull RegisterUserRequest dto) {
        return  User.builder()
                .id(dto.id())
                .username(dto.username())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(dto.password())
                .phone(dto.phone())
                .roles(dto.roles().stream().map(role -> {
                        Role newRole = new Role();
                        newRole.setName(role);
                        return newRole;
                    }).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public User fromUserDTO(@NonNull UserDto dto) {
        return  User.builder()
                .id(dto.id())
                .username(dto.username())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(dto.password())
                .roles(dto.roles())
                .build();
    }

    @Override
    public UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getRoles()
        );
    }
}

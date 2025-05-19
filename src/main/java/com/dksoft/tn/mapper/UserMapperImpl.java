package com.dksoft.tn.mapper;

import org.springframework.stereotype.Service;

import com.dksoft.tn.dto.UserDto;
import com.dksoft.tn.entity.User;

import lombok.NonNull;
@Service
public class UserMapperImpl implements UserMapper {


    @Override
    public User fromUserDTO(@NonNull UserDto dto) {
        return  User.builder().id(dto.id()).username(dto.username()).name(dto.name())
                .email(dto.email()).password(dto.password()).roles(dto.roles()).build();
    }

    @Override
    public UserDto fromUser(User user) {
        return new UserDto(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRoles());
    }
}

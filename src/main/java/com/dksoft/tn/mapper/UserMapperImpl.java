package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.RegisterUserRequest;
import com.dksoft.tn.dto.UserDto;
import com.dksoft.tn.entity.User;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public User fromRegisterUserRequest(@NonNull RegisterUserRequest userDto) {
        return User.builder()
                .id(userDto.id())
                .username(userDto.username())
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .email(userDto.email())
                .password(userDto.password())
                .phone(userDto.phone())
                .organizationName(userDto.organizationName())
                .organizationDescription(userDto.organizationDescription())
                .isApproved(userDto.isApproved())
                .build();
    }

    @Override
    public User fromUserDTO(@NonNull UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .username(userDto.username())
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .email(userDto.email())
                .phone(userDto.phone())
                .organizationName(userDto.organizationName())
                .organizationDescription(userDto.organizationDescription())
                .isApproved(userDto.isApproved())
                .isActive(userDto.isActive())
                .roles(userDto.roles())
                .build();
    }

    @Override
    public UserDto fromUser(@NonNull User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getOrganizationName(),
                user.getOrganizationDescription(),
                user.isApproved(),
                user.isActive(),
                user.getRoles()
        );
    }
}
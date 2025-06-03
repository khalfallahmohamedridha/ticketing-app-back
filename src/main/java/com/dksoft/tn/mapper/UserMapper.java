package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.RegisterUserRequest;
import com.dksoft.tn.dto.UserDto;
import com.dksoft.tn.entity.User;
import lombok.NonNull;

public interface UserMapper {

    User fromRegisterUserRequest(@NonNull RegisterUserRequest dto);

    User fromUserDTO(@NonNull UserDto dto);

    UserDto fromUser(User user);
}

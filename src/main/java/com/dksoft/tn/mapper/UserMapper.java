package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.UserDto;
import com.dksoft.tn.entity.User;
import lombok.NonNull;

public interface UserMapper {

    User fromUserDTO(@NonNull UserDto dto);

    UserDto fromUser(User user);
}

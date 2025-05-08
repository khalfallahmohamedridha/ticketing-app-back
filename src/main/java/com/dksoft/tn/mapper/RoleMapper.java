package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.RoleDto;
import com.dksoft.tn.entity.Role;
import lombok.NonNull;


public interface RoleMapper {

    Role fromRoleDto( @NonNull RoleDto roleDto);

    RoleDto fromRole(Role role);
}

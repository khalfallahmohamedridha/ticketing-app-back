package com.dksoft.tn.mapper;

import com.dksoft.tn.dto.RoleDto;
import com.dksoft.tn.entity.Role;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RoleMapperImpl implements RoleMapper {


    @Override
    public Role fromRoleDto(@NonNull RoleDto roleDto) {
        return Role.builder().id(roleDto.id()).name(roleDto.name()).build();


    }

    @Override
    public RoleDto fromRole(Role role) {

        return new RoleDto(role.getId(), role.getName());
    }
}

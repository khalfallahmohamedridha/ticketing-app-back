package com.dksoft.tn.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dksoft.tn.dto.RoleDto;
import com.dksoft.tn.entity.Role;
import com.dksoft.tn.exception.RoleNotFoundException;
import com.dksoft.tn.mapper.RoleMapper;
import com.dksoft.tn.repository.RoleRepository;

@Service
@Transactional

public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleMapper mapper;

    public RoleDto save(RoleDto roledto) {
        Role role = mapper.fromRoleDto(roledto);
        Role roleSaved = roleRepository.save(role);
        return mapper.fromRole(roleSaved);
    }
    public RoleDto update(Long id, RoleDto roleDto) throws RoleNotFoundException {
        Role role = roleRepository.findById(id)
                .orElseThrow( ()-> new RoleNotFoundException("role you try to update with id = '"+id+"' not found."));
        updateRoleFields(role, roleDto);
        Role roleUpdate = roleRepository.save(role);
        return mapper.fromRole(roleUpdate);
    }
    private void updateRoleFields(@NotNull Role role, @NotNull RoleDto roleDto){
        role.setName(roleDto.name());

    }
}

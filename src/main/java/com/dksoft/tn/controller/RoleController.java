package com.dksoft.tn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dksoft.tn.dto.RoleDto;
import com.dksoft.tn.exception.RoleNotFoundException;
import com.dksoft.tn.service.RoleService;

@RestController
@RequestMapping("/roles")

public class RoleController {

    @Autowired
    RoleService roleService;
    @PostMapping("/save")
    public RoleDto save(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    @PutMapping("/update/{id}")
    public RoleDto update(@PathVariable long id , @RequestBody RoleDto roleDto) throws RoleNotFoundException {
        return roleService.update(id, roleDto);
    }
}

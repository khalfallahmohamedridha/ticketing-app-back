package com.dksoft.tn.controller;

import com.dksoft.tn.dto.UserDto;
import com.dksoft.tn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
     UserService userService;

    @PostMapping("/save")
    public UserDto save( @RequestBody UserDto userDto) {
        return userService.save(userDto);
    }
}

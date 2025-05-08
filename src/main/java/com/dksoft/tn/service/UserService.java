package com.dksoft.tn.service;

import com.dksoft.tn.dto.UserDto;
import com.dksoft.tn.entity.Role;
import com.dksoft.tn.entity.User;
import com.dksoft.tn.exception.RoleNotFoundException;
import com.dksoft.tn.mapper.UserMapper;
import com.dksoft.tn.repository.RoleRepository;
import com.dksoft.tn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDto save(UserDto userDto) throws RoleNotFoundException {
        Set<Role> roles = userDto.roles().stream()
                .map(role -> roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RoleNotFoundException("Role with ID " + role.getId() + " not found"))) // Utilisation de RoleNotFound
                .collect(Collectors.toSet());

        User user = userMapper.fromUserDTO(userDto);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userDto.password()));

        User userSaved = userRepository.save(user);
        return userMapper.fromUser(userSaved);
    }

}
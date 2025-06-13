package com.dksoft.tn.service;

import com.dksoft.tn.dto.RegisterUserRequest;
import com.dksoft.tn.dto.UserDto;
import com.dksoft.tn.entity.Role;
import com.dksoft.tn.entity.User;
import com.dksoft.tn.exception.RoleNotFoundException;
import com.dksoft.tn.exception.UserNotFoundException;
import com.dksoft.tn.mapper.UserMapper;
import com.dksoft.tn.repository.RoleRepository;
import com.dksoft.tn.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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

    public UserDto save(RegisterUserRequest userDto) throws RoleNotFoundException {
        Set<Role> roles = userDto.roles().stream()
                .map(role -> roleRepository.findByName(role)
                        .orElseThrow(() -> new RoleNotFoundException("Role with Name " + role + " not found")))
                .collect(Collectors.toSet());

        log.info("Saving user with roles {}", roles);
        User user = userMapper.fromRegisterUserRequest(userDto);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setActive(true);
        user.setApproved(roles.stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")) ? userDto.isApproved() : false);

        log.info("Saving user {}", user);
        User userSaved = userRepository.save(user);
        log.info("User is saved!");
        return userMapper.fromUser(userSaved);
    }

    public UserDto updateUser(String username, RegisterUserRequest userDto) throws UserNotFoundException {
        log.info("Updating user with username {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setPhone(userDto.phone());
        user.setOrganizationName(userDto.organizationName());
        user.setOrganizationDescription(userDto.organizationDescription());

        if (userDto.password() != null && !userDto.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.password()));
        }

        log.info("Updating user {}", user);
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully!");
        return userMapper.fromUser(updatedUser);
    }

    public List<UserDto> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::fromUser)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) throws UserNotFoundException {
        log.info("Deleting user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setActive(false);
        userRepository.save(user);
        log.info("User {} deactivated successfully", id);
    }

    public UserDto updateApprovalStatus(Long id, boolean isApproved, String rejectionReason) throws UserNotFoundException {
        log.info("Updating approval status for user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setApproved(isApproved);
        User updatedUser = userRepository.save(user);
        log.info("User {} approval status updated to {}", id, isApproved);
        // TODO: Implémenter la notification par email pour le rejet avec rejectionReason
        return userMapper.fromUser(updatedUser);
    }
}
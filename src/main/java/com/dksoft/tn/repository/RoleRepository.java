package com.dksoft.tn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dksoft.tn.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}

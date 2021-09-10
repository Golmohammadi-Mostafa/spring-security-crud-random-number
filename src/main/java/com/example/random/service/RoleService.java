package com.example.random.service;

import com.example.random.domain.Role;
import com.example.random.enums.RoleType;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleType roleName);
}

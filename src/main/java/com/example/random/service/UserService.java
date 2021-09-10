package com.example.random.service;

import com.example.random.dto.JwtTokenDTO;
import com.example.random.dto.SingUpDTO;
import com.example.random.dto.UserResponseDTO;
import com.example.random.enums.UserStatus;

public interface UserService {
    JwtTokenDTO signIn(String username, String password);

    JwtTokenDTO signUp(SingUpDTO dto);

    void delete(String username);

    UserResponseDTO search(String username);

    UserResponseDTO whoAmI(String userName);

    JwtTokenDTO refresh(String username);

    UserResponseDTO changeUserStatus(String username, UserStatus userStatus);

    void deleteAllUsers();
}

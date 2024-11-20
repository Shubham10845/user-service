package com.userservice.service;

import com.userservice.dto.LoginRequestDTO;
import com.userservice.dto.LoginResponseDTO;
import com.userservice.dto.SignUpRequestDTO;
import com.userservice.dto.UserDTO;

public interface UserService {
    public UserDTO signup(SignUpRequestDTO signUpRequestDTO);
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    public UserDTO validateToken(String token);
}

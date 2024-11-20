package com.userservice.service;

import com.userservice.dto.*;

public interface UserService {
    public UserDTO signup(SignUpRequestDTO signUpRequestDTO);
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    public UserDTO validateToken(String token);
    public String logout(LogoutRequestDTO logoutRequestDTO);
}

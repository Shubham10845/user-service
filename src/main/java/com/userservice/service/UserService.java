package com.userservice.service;

import com.userservice.dto.SignUpRequestDTO;
import com.userservice.dto.UserDTO;

public interface UserService {
    public UserDTO signup(SignUpRequestDTO signUpRequestDTO);
}

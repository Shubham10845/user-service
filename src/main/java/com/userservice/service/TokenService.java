package com.userservice.service;

import com.userservice.dto.LoginRequestDTO;
import org.springframework.security.core.Authentication;

public interface TokenService {
    public String generateToken(Authentication authentication);

}

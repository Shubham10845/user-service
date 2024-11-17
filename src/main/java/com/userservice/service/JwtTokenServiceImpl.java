package com.userservice.service;

import com.userservice.constants.ApplicationConstants;
import com.userservice.dto.LoginRequestDTO;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenServiceImpl implements TokenService{

    @Override
    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(ApplicationConstants.SUBJECT)
                .claim("email", authentication.getName()) // Store username as a custom claim
                .claim("role", authentication.getAuthorities()) // Add custom claims
                .setIssuer(ApplicationConstants.ISSUER)
                .setIssuedAt(new Date()) // Set issued date
                .setExpiration(new Date(System.currentTimeMillis() + ApplicationConstants.EXPIRATION_TIME)) // Set expiration
                .signWith(ApplicationConstants.SECRET_KEY) // Sign the token with the secret key
                .compact(); // Build the token
    }
}
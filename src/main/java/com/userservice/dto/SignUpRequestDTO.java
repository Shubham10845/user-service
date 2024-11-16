package com.userservice.dto;

import com.userservice.model.Role;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpRequestDTO {
    private String email;
    private String password;
    private String name;
    private String role;
}

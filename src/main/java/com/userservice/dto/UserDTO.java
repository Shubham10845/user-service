package com.userservice.dto;

import com.userservice.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String name;
    private Role role;
}

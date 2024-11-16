package com.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User {
    private String email;
    private String hashedPassword;
    private String name;
    private boolean isEmailVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}

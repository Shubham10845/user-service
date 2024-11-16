package com.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String email;
    private String hashedPassword;
    private String name;
    private boolean isEmailVerified;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Role role;
}

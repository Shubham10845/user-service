package com.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModel{
    @Lob
    @Column(columnDefinition = "TEXT")
    private String value;

    @ManyToOne
    private User user;
    private Date expiryAt;
}

package com.npro.UserManagementService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "RevokedTokens")
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min =1, max = 255)
    @Column(unique = true, nullable = false)
    private String token;

    @NotNull
    private Date expiresAt;


    public RevokedToken(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;

    }
}

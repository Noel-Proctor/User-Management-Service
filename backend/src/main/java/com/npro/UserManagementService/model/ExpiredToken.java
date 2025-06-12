package com.npro.UserManagementService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ExpiredTokens")
public class ExpiredToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min =1, max = 255)
    @Column(unique = true, nullable = false)
    private final String token;


    public ExpiredToken(String token) {
        this.token = token;
    }
}

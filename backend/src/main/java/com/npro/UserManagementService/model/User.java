package com.npro.UserManagementService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Size(min = 3, max = 50, message= "Username must be between 3-50 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(min = 10, max = 50)
    private String passwordHash;

    @NotNull
    private boolean isActive;

//    Basic User, User Manager or Administrator
    @NotNull
    private System_Role system_role;

    @NotNull
    private String email;


    @OneToMany
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Application> applications = new HashSet<>();

    @NotNull
    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(@NotNull boolean active) {
        isActive = active;
    }

    public User(String username, String passwordHash, boolean isActive) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.isActive = isActive;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setSystemRole(@NotNull System_Role system_role) {
        this.system_role=  system_role;
    }

    public System_Role getSystem_role() {
        return system_role;
    }

    public @NotBlank @Size(min = 3, max = 50) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 50) String username) {
        this.username = username;
    }

    public @NotBlank @Size(min = 10, max = 50) String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@NotBlank @Size(min = 10, max = 50) String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void add_Roles(Role role) {
        roles.add(role);
    }

    public Set<Application> get_OwnedApplications() {
        return applications;
    }

    public void add_OwnedApplication(Application application) {
        applications.add(application);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }
}

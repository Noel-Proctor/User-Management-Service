package com.npro.UserManagementService.payload;

import com.npro.UserManagementService.model.System_Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @Size(min = 2, max = 100, message = "Username must be between 2-100 characters long")
    private String username;

    @Email(message = "Email is not valid", regexp = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull
    private System_Role system_role;

    @Size(min = 8, message = "Password must be 8 or more characters long")
    private String passwordHash;

    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.passwordHash = password;
    }

    public UserDTO() {
    }

    public System_Role getSystem_role() {
        return system_role;
    }

    public void setSystem_role(@NotNull System_Role system_role) {
        this.system_role = system_role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @Size(min = 8, message = "Password must be 8 or more characters long") String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@Size(min = 8, message = "Password must be 8 or more characters long") String passwordHash) {
        this.passwordHash = passwordHash;
    }
}

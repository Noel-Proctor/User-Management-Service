package com.npro.UserManagementService.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Size(min = 1, max = 50, message = "Role Name must be be between 1-50 characters.")
    private String roleName;

    @NotNull
    @ManyToOne
    private Application application;

//  Flag to toggle if the role is an admin role for the application.
    @NotNull
    private boolean isApplicationAdmin =false;

    @NotNull
    private boolean active = true;

    public Role(String roleName, Application application, boolean isApplicationAdmin, boolean active) {
        this.roleName = roleName;
        this.application = application;
        this.isApplicationAdmin = isApplicationAdmin;
        this.active = active;
    }

    public Role() {
    }

    public Integer getId() {
        return Id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(@Size(min = 1, max = 50, message = "Role Name must be be between 1-50 characters.") String roleName) {
        this.roleName = roleName;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(@NotNull Application application) {
        this.application = application;
    }

    @NotNull
    public boolean isApplicationAdmin() {
        return isApplicationAdmin;
    }

    public void setApplicationAdmin(@NotNull boolean applicationAdmin) {
        isApplicationAdmin = applicationAdmin;
    }

    @NotNull
    public boolean isActive() {
        return active;
    }

    public void setActive(@NotNull boolean active) {
        this.active = active;
    }
}

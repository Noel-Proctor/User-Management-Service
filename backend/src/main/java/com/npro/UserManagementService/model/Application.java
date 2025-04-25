package com.npro.UserManagementService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Application {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer Id;

    @NotNull
    @Size(min = 1, max = 50, message = "Application name must be between 1-50 characters long")
    @Column(unique = true, nullable = false)
    private String application_name;

    @OneToMany(mappedBy = "application")
    private List<Role> application_roles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "application_owner")
    private Users owner;

    public int getId() {
        return Id;
    }

    public @NotNull @Size(min = 1, max = 50, message = "Application name must be between 1-50 characters long") String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(@NotNull @Size(min = 1, max = 50, message = "Application name must be between 1-50 characters long") String application_name) {
        this.application_name = application_name;
    }

    public List<Role> getApplication_roles() {
        return application_roles;
    }

    public void setApplication_roles(List<Role> application_roles) {
        this.application_roles = application_roles;
    }

    public void addApplication_role(Role application_role) {
        this.application_roles.add(application_role);
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}

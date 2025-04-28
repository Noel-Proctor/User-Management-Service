package com.npro.UserManagementService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Role> application_roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "application_owner")
    @NotNull
    private Users owner;

    public int getId() {
        return Id;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(@NotNull
                                    @Size(min = 1, max = 50, message = "Application name must be between 1-50 characters long") String application_name) {
        this.application_name = application_name;
    }

    public Set<Role> getApplication_roles() {
        return application_roles;
    }

    public void addApplication_role(@NotNull Role application_role) {

        if (application_role != null) {
            this.application_roles.add(application_role);
        }
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}

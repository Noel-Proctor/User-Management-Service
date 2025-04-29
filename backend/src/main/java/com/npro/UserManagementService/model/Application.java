package com.npro.UserManagementService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
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

    @NotNull
    @Size(min = 1, max = 50, message = "Application description must be between 1-50 characters long")
    private String application_description;

    @NotNull
    @Size(min = 1, max = 50, message = "Guid must be between 1-50 characters long")
    private String guid;

    @OneToMany(mappedBy = "application")
    private Set<Role> application_roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "application_owner")
    @NotNull
    private User owner;

    @NotNull
    @FutureOrPresent
    private LocalDate createdOn;

    public void setCreatedOn(@NotNull @FutureOrPresent LocalDate createdOn) {
        this.createdOn = createdOn;
    }

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

    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        if(owner.getSystem_role() != System_Role.USER_MANAGER){
            throw new IllegalArgumentException("Only User Managers can be the owner of an application");
        }
        this.owner = owner;
    }

    public String getApplication_description() {
        return application_description;
    }

    public void setApplication_description(@NotNull @Size(min = 1, max = 50, message = "Application description must be between 1-50 characters long") String application_description) {
        this.application_description = application_description;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(@NotNull @Size(min = 1, max = 50, message = "Guid must be between 1-50 characters long") String guid) {
        this.guid = guid;
    }
}

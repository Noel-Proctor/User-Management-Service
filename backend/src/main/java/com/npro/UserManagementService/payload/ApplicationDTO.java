package com.npro.UserManagementService.payload;

import com.npro.UserManagementService.model.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ApplicationDTO {

    private String applicationName;
    private String applicationDescription;
    private LocalDate createdOn;
    private String guid;

//    private UserDTO owner;
    private List<Role> applicationRoles;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public List<Role> getApplicationRoles() {
        return applicationRoles;
    }

    public void setApplicationRoles(List<Role> applicationRoles) {
        this.applicationRoles = applicationRoles;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public static class ApplicationDTOBuilder{

        public static ApplicationDTO buildTestDTO(){
            ApplicationDTO applicationDTO = new ApplicationDTO();
            applicationDTO.setApplicationName("Test Application Name");
//            applicationDTO.setApplicationDescription("Test Application DTO Description");
            applicationDTO.setCreatedOn(LocalDate.now());
            applicationDTO.setGuid(UUID.randomUUID().toString());

            return applicationDTO;
        }
    }

}

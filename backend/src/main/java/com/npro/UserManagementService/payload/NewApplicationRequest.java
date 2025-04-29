package com.npro.UserManagementService.payload;


import jakarta.validation.constraints.Size;

public class NewApplicationRequest {

    @Size(min = 1, max = 50, message = "Application name must be between 1-50 characters long")
    private String application_name;

    @Size(min = 1, max = 50, message = "Application description must be between 1-50 characters long")
    private String application_description;

    public NewApplicationRequest(String application_name, String application_description) {
        this.application_name = application_name;
        this.application_description = application_description;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getApplication_description() {
        return application_description;
    }

    public void setApplication_description(String application_description) {
        this.application_description = application_description;
    }
}

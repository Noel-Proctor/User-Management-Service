package com.npro.UserManagementService.payload;

public class ApplicationResponse {

    private String message;
    private ApplicationDTO application;

    public ApplicationResponse(String message, ApplicationDTO application) {
        this.message = message;
        this.application = application;
    }

    public ApplicationResponse() {
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {}

    public ApplicationDTO getApplication() {
        return application;
    }
    public void setApplication(ApplicationDTO application) {}
}

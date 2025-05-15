package com.npro.UserManagementService.controllers;

import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.ApplicationResponse;
import com.npro.UserManagementService.payload.NewApplicationRequest;
import com.npro.UserManagementService.service.ApplicationService;
import com.npro.UserManagementService.utils.UserFactory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserFactory userFactory;

    public ApplicationController(ApplicationService applicationService, UserFactory userFactory) {
        this.applicationService = applicationService;
        this.userFactory = userFactory;
    }

    @PostMapping()
    public ResponseEntity<ApplicationResponse> createApplication(@Valid @RequestBody NewApplicationRequest newApplicationRequest) {
//  Making a dummy user. this will be handled by spring security next.

        User user = userFactory.getDefaultAdminUser();
        ApplicationResponse response = new ApplicationResponse(
                "Application created successfully",
                applicationService.createNewApplication(user, newApplicationRequest.getApplication_name(), newApplicationRequest.getApplication_description())
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

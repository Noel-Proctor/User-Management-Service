package com.npro.UserManagementService.service;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.ApplicationDTO;





public interface ApplicationService {

    ApplicationDTO createNewApplication(User user, String applicationName, String applicationDescription);
}

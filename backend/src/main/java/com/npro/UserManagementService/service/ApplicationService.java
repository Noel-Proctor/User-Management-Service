package com.npro.UserManagementService.service;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.ApplicationDTO;
import org.springframework.stereotype.Service;



@Service
public interface ApplicationService {

    ApplicationDTO createNewApplication(User user, String applicationName, String applicationDescription);
}

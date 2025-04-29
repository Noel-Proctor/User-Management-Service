package com.npro.UserManagementService.utils;

import com.npro.UserManagementService.exceptions.AccessDeniedRuntimeException;
import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthorisationService {

    public static void authoriseCreateNewApplication(User user) {
        if(user.getSystem_role()!= System_Role.USER_MANAGER){
            throw new AccessDeniedRuntimeException("Only User Managers can add an application");
        }
    }

    // Updating name of application
    // delete application  -- Only owner should be able to do this.
    // Get single application
    // Get all applications paginated.



}

package com.npro.UserManagementService.service;

import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.payload.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponse createNewUser(UserDTO userRequest);


}

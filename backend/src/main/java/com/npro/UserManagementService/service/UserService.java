package com.npro.UserManagementService.service;

import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.payload.UserPage;
import com.npro.UserManagementService.payload.UserResponse;



public interface UserService {

    UserResponse createNewUser(UserDTO userRequest);


    UserPage getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String direction);
}

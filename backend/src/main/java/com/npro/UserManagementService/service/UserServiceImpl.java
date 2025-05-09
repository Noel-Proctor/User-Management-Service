package com.npro.UserManagementService.service;

import com.npro.UserManagementService.exceptions.APIException;
import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.payload.UserResponse;
import com.npro.UserManagementService.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponse createNewUser(UserDTO newUserRequest){

//If username already exists, throw APIException
       userRepository.findByUsername(newUserRequest.getUsername()).ifPresent(user -> {
           throw new APIException("Username already exists.");
       });

       User user = modelMapper.map(newUserRequest, User.class);

       switch (newUserRequest.getSystem_role()){
           case ADMIN:
               user.setSystemRole(System_Role.ADMIN);
//TODO:Once I have user sesssion created I need to come back here and only let Admins create
// other admins and User Managers
               break;
           case BASIC_USER:
//TODO: Ensure only Admins and User Managers can create Basic Users.
               user.setSystemRole(System_Role.BASIC_USER);
               break;
           case USER_MANAGER:
//TODO:Once I have user sesssion created I need to come back here and only let Admins create
//    other admins and User Managers
               user.setSystemRole(System_Role.USER_MANAGER);
               break;
           default:
               user.setSystemRole(System_Role.BASIC_USER);
       }

       user.setSystemRole(newUserRequest.getSystem_role());
       User savedUser = userRepository.save(user);
       UserResponse userResponse = new UserResponse("User created successfully", modelMapper.map(savedUser, UserDTO.class));

       return userResponse;









    }
}

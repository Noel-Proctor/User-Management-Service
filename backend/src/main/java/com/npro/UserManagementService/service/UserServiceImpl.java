package com.npro.UserManagementService.service;

import com.npro.UserManagementService.exceptions.APIException;
import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.payload.UserPage;
import com.npro.UserManagementService.payload.UserResponse;
import com.npro.UserManagementService.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
       user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
       User savedUser = userRepository.save(user);
       UserResponse userResponse = new UserResponse("User created successfully", modelMapper.map(savedUser, UserDTO.class));

       return userResponse;
    }

    @Override
    public UserPage getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        Sort sortAndOrderBy = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortAndOrderBy);

        Page<User> page = userRepository.findAll(pageable);

        List<UserResponse> users = page.getContent().stream().map(
                user-> new UserResponse("User Found", modelMapper.map(user, UserDTO.class))).toList();

        UserPage userPage = new UserPage();
        userPage.setCurrentPage(page.getNumber());
        userPage.setTotalPages(page.getTotalPages());
        userPage.setFirstPage(page.isFirst());
        userPage.setLastPage(page.isLast());
        userPage.setTotalPages(page.getTotalPages());
        userPage.setUserList(users);
        return userPage;
    }




}

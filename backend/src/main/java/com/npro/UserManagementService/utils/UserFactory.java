package com.npro.UserManagementService.utils;

import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFactory {

    private final UserRepository userRepository;

    public UserFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static User buildDefaultUserManager(){
        User user = new User();
        user.setUsername("userManager");
        user.setPasswordHash("<PASSWORD>");
        user.setSystemRole(System_Role.USER_MANAGER);
        return user;
    }

    public static User buildDefaultBaseUser(){
        User user = new User();
        user.setUsername("BaseUser");
        user.setPasswordHash("<PASSWORD>");
        user.setSystemRole(System_Role.BASIC_USER);
        return user;
    }

    public static User buildDefaultAdminUser(){
        User user = new User();
        user.setUsername("AdminUser");
        user.setPasswordHash("<PASSWORD>");
        user.setSystemRole(System_Role.ADMIN);
        return user;
    }

    public User getDefaultAdminUser(){

        Optional<User> user = userRepository.findByUsername("Noel_ADMIN");
        if(user.isEmpty()){
           User admin = userRepository.save(buildDefaultAdminUser());
           return admin;
        }
        return user.get();
    }
}

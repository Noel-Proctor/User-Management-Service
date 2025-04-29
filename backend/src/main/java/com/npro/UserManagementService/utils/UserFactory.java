package com.npro.UserManagementService.utils;

import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;

public class UserFactory {

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
}

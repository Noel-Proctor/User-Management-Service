package com.npro.UserManagementService.controllers;

import com.npro.UserManagementService.payload.LoginResponse;
import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponse Login(@RequestBody UserDTO user) {

        return userService.verify(user);
    }
}

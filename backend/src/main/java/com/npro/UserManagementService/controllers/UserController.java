package com.npro.UserManagementService.controllers;

import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.payload.UserPage;
import com.npro.UserManagementService.payload.UserResponse;
import com.npro.UserManagementService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDTO user){
        UserResponse response = userService.createNewUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<UserPage> getUserPage(@RequestParam(name = "pageNumber", default)Integer pageNumber ){
//        UserPage response = userService.getAllUsers(pageNumber, pageSize, sortBy, direction);
//        retrun new ResponseEntity<>(response, HttpStatus.OK);
//
//    }


}

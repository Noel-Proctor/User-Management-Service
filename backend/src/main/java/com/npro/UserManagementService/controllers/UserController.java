package com.npro.UserManagementService.controllers;

import com.npro.UserManagementService.config.AppConstants;
import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.payload.UserPage;
import com.npro.UserManagementService.payload.UserResponse;
import com.npro.UserManagementService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
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

    @GetMapping
    public ResponseEntity<UserPage> getUserPage(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER)Integer pageNumber,
                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
                                @RequestParam(name = "direction", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String direction){

        UserPage response = userService.getAllUsers(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}

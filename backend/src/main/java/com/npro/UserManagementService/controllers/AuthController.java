package com.npro.UserManagementService.controllers;


import com.npro.UserManagementService.Security.JWTService;
import com.npro.UserManagementService.config.AppConstants;
import com.npro.UserManagementService.payload.LoginResponse;
import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTService jwtService;
    private final UserService userService;

    public AuthController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<Map<String, String>> refreshTokens(HttpServletRequest request,
                                                             @CookieValue("refresh_token") String refresh_token){
        Map<String, String> tokens = jwtService.refreshTokens(refresh_token, request);
        ResponseCookie cookie = ResponseCookie.from(tokens.get("refresh_Token")).httpOnly(true).secure(true)
                .sameSite("Strict").path("/auth/refreshToken").maxAge(Duration.ofMillis(AppConstants.REFRESH_TOKEN_VALIDITY))
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("access_Token", tokens.get("access_Token")));

    }


//    @PostMapping("/login")
//    public LoginResponse Login(@RequestBody UserDTO user) {
//
//        return userService.verify(user);
//    }

}

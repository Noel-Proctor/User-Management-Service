package com.npro.UserManagementService.controllers;


import com.npro.UserManagementService.Security.JWTService;
import com.npro.UserManagementService.config.AppConstants;

import com.npro.UserManagementService.service.TokenService;
import com.npro.UserManagementService.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.util.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTService jwtService;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(JWTService jwtService, UserService userService, TokenService tokenService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<Map<String, String>> refreshTokens(HttpServletRequest request,
                                                             @CookieValue("refreshToken") String refreshToken){
        System.out.println("Refresh: "+refreshToken);

        Map<String, String> tokens = jwtService.refreshTokens(refreshToken, request);
        ResponseCookie cookie = ResponseCookie.from(tokens.get("refreshToken")).httpOnly(true).secure(true)
                .sameSite("Strict").path("/auth").maxAge(Duration.ofMillis(AppConstants.REFRESH_TOKEN_VALIDITY))
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("accessToken", tokens.get("accessToken")));

    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, @CookieValue("refreshToken") String refreshToken){

        System.out.println("Token: "+refreshToken);
        ResponseCookie deletionCookie = ResponseCookie
                .from("refreshToken", "")
                .path("/auth")
                .sameSite("Strict")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deletionCookie.toString());
        SecurityContextHolder.clearContext();
    }

    private String extractToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return null;
        }

        String token = Arrays.stream(cookies)
                .filter(c-> "refreshToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        return token;

    }
}

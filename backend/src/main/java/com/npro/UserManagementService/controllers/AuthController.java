package com.npro.UserManagementService.controllers;


import com.npro.UserManagementService.Security.JWTService;
import com.npro.UserManagementService.config.AppConstants;

import com.npro.UserManagementService.service.TokenService;
import com.npro.UserManagementService.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.http.*;
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
                                                             @CookieValue("refreshToken") String refreshToken, HttpServletResponse response){
        System.out.println("Refresh: "+refreshToken);


        if(tokenService.checkIfTokenIsRevoked(refreshToken)){
            ResponseCookie resetCookie = jwtService.getResetCookie();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, resetCookie.toString())
                    .body(Map.of("error", "token revoked"));

        }else{
            Map<String, String> tokens = jwtService.refreshTokens(refreshToken, request);
            ResponseCookie cookie = jwtService.getRefreshCookie(tokens.get("refreshToken"));
//        ResponseCookie cookie = ResponseCookie.from(tokens.get("refreshToken")).httpOnly(true).secure(true)
//                .sameSite("Strict").path("/auth").maxAge(Duration.ofMillis(AppConstants.REFRESH_TOKEN_VALIDITY))
//                .build()
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(Map.of("accessToken", tokens.get("accessToken")));



        }


    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, @CookieValue("refreshToken") String refreshToken){
//In future could also save access token to validate that the access token has not been revoked.
        tokenService.saveRevokedToken(refreshToken);
        System.out.println("Token: "+refreshToken);
//        ResponseCookie deletionCookie = ResponseCookie
//                .from("refreshToken", "")
//                .path("/auth")
//                .sameSite("Strict")
//                .httpOnly(true)
//                .secure(true)
//                .maxAge(0)
//                .build();

        ResponseCookie resetCookie = jwtService.getResetCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, resetCookie.toString());
        SecurityContextHolder.clearContext();
    }
}

package com.npro.UserManagementService.Security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npro.UserManagementService.config.AppConstants;
import com.npro.UserManagementService.model.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//    private final String secret;
    private final JWTService jwtService;

    public CustomAuthenticationFilter(AuthenticationManager authManager, JWTService jwtService) {
        setAuthenticationManager(authManager);
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("Username is: "+ username);
        logger.info("Password is:" + password);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Map<String, String> tokens = jwtService.generateTokens(user, request);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.get("refreshToken"))
                .httpOnly(true)
                .sameSite("Strict")
                .secure(true)
                .path("/auth")
                .maxAge(Duration.ofMillis(AppConstants.REFRESH_TOKEN_VALIDITY))
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setContentType(APPLICATION_JSON_VALUE);
        tokens.remove("refreshToken");
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
    }
}


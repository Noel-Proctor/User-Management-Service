package com.npro.UserManagementService.Security;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthorisationFilter extends OncePerRequestFilter {


    private final JWTService jwtService;
    private static final String LOGIN_PATH = "/login";

    public CustomAuthorisationFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {

            try{
                String authHeader = request.getHeader(AUTHORIZATION);
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    String token = authHeader.substring("Bearer ".length());
                    UsernamePasswordAuthenticationToken authToken = jwtService.validateToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                }else {
                    filterChain.doFilter(request, response);
                }
            }catch (Exception e){
                response.setHeader("error", "Token Invalid");
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setStatus(FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error","Token Invalid");
                new ObjectMapper().writeValue(response.getOutputStream(),errors);
            }
        }

    //Skips filter if current path is login path.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return LOGIN_PATH.equals(request.getServletPath());
    }

}

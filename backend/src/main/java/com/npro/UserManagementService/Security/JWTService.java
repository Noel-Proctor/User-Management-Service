package com.npro.UserManagementService.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.npro.UserManagementService.config.AppConstants;
import com.npro.UserManagementService.exceptions.APIException;
import com.npro.UserManagementService.model.UserPrincipal;
import com.npro.UserManagementService.service.CustomUserDetailsServiceImpl;
import com.npro.UserManagementService.service.TokenService;
import com.npro.UserManagementService.service.TokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;

@Service
public class JWTService {

    private final CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
    private final String secret;
    protected final Log logger = LogFactory.getLog(getClass());
    private final Algorithm algorithm;//
    private final JWTVerifier verifier;// 7 days
    private final TokenService tokenService;

    public JWTService(@Value("${spring.security.secret}") String secret, CustomUserDetailsServiceImpl customUserDetailsServiceImpl, TokenService tokenService) {
        this.algorithm = Algorithm.HMAC256(secret.getBytes());
        this.verifier = JWT.require(algorithm).build();
        this.secret = secret;
        this.customUserDetailsServiceImpl = customUserDetailsServiceImpl;
        this.tokenService = tokenService;
    }

    private String generateAccessToken(UserPrincipal user, HttpServletRequest request) {
        String access_Token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AppConstants.ACCESS_TOKEN_VALIDITY))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);

        return access_Token;
    }


    private String generateRefreshToken(UserPrincipal user, HttpServletRequest request) {
        String refresh_Token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AppConstants.REFRESH_TOKEN_VALIDITY))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        return refresh_Token;
    }

    public Map<String, String> generateTokens(UserPrincipal user, HttpServletRequest request) {
        String accessToken = generateAccessToken(user, request);
        String refreshToken = generateRefreshToken(user, request);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

//    private SecretKey getKey(){
//        byte [] keyBytes = Decoders.BASE64.decode();
//        return Keys.hmacShaKeyFor(keyBytes);
//    }


//    public String extractUsername(String token) {
//
//        return extractClaim(token, Claims::getSubject);
//    }


//    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token){
//        return Jwts.parser().verifyWith(algorithm)
//                .build().parseSignedClaims(token).getPayload();
//    }

    /**
     * @param token JWT TOKEN
     * @return UsernamePasswordAuthenticationToken
     * Receives a Token and returns a UsernamePasswordAuthenticationToken if token is valid.
     * Else throws exception.
     */
    public UsernamePasswordAuthenticationToken validateToken(String token) {
        try {
            DecodedJWT decodedJTW = verifier.verify(token);
            String username = decodedJTW.getSubject();
            String[] roles = decodedJTW.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(
                    role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }
            );
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                    null, authorities);
            return authToken;
        } catch (Exception e) {
            logger.error("Token Validation failed {+" + e.getMessage() + "}", e);
            throw e;
        }
    }

    public Map<String, String> refreshTokens(String token, HttpServletRequest request) {

        try {
            DecodedJWT decodedJTW = verifier.verify(token);
            String username = decodedJTW.getSubject();
            UserPrincipal user = customUserDetailsServiceImpl.loadUserByUsername(username);
            return generateTokens(user, request);

        } catch (JWTVerificationException e) {
            tokenService.saveRevokedToken(token);
            throw new APIException("Invalid Credentials");
        } catch (Exception e) {
            throw new APIException("Invalid Credentials");
        }
    }

    public ResponseCookie getRefreshCookie(String token){
        ResponseCookie cookie = ResponseCookie
                .from("refreshToken", token)
                .httpOnly(true)
                .sameSite("Strict")
                .secure(true)
                .path("/auth")
                .maxAge(Duration.ofMillis(AppConstants.REFRESH_TOKEN_VALIDITY))
                .build();

        return cookie;

    }


    public ResponseCookie getResetCookie(){
        ResponseCookie resetCookie = ResponseCookie
                .from("refreshToken", "")
                .httpOnly(true)
                .sameSite("Strict")
                .secure(true)
                .path("/auth")
                .maxAge(0)
                .build();

        return resetCookie;

    }
}


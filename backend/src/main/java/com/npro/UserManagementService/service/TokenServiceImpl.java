package com.npro.UserManagementService.service;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.npro.UserManagementService.model.RevokedToken;
import com.npro.UserManagementService.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;

@Service
public class TokenServiceImpl implements TokenService {

    private final Algorithm algorithm;//
    private final JWTVerifier verifier;
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(@Value("${spring.security.secret}") String secret, TokenRepository tokenRepository) {
        this.algorithm = Algorithm.HMAC256((secret.getBytes()));
        this.verifier = JWT.require(algorithm).build();
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void saveRevokedToken(String refreshToken) {
        DecodedJWT decodedToken = verifier.verify(refreshToken);
        RevokedToken token = new RevokedToken(refreshToken, decodedToken.getExpiresAt());
        tokenRepository.save(token);

    }

    @Override
    public boolean checkIfTokenIsRevoked(String refreshToken) {
        return tokenRepository.findByToken(refreshToken).isPresent();
    }


}

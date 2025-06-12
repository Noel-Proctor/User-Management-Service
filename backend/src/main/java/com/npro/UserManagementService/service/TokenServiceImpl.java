package com.npro.UserManagementService.service;


import com.npro.UserManagementService.model.ExpiredToken;
import com.npro.UserManagementService.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {


    private TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void saveExpiredToken(String refreshToken) {

        ExpiredToken token = new ExpiredToken(refreshToken);
        tokenRepository.save(token);

    }
}

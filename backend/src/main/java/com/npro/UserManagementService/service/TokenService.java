package com.npro.UserManagementService.service;


public interface TokenService {
    void saveRevokedToken(String refreshToken);

    boolean checkIfTokenIsRevoked(String refreshToken);
}

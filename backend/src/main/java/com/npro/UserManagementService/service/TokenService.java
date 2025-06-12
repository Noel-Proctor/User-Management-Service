package com.npro.UserManagementService.service;


public interface TokenService {
    void saveExpiredToken(String refreshToken);
}

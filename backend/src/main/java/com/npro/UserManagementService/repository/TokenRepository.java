package com.npro.UserManagementService.repository;

import com.npro.UserManagementService.model.ExpiredToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<ExpiredToken, Long> {
    Optional<ExpiredToken> findByToken(String token);
}

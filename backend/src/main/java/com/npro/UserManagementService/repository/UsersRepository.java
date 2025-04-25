package com.npro.UserManagementService.repository;

import com.npro.UserManagementService.model.Users;
import com.npro.UserManagementService.model.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(@NotBlank @Size(min = 3, max = 50) String username);
}

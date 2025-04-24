package com.npro.UserManagementService.repository;

import com.npro.UserManagementService.model.Users;
import com.npro.UserManagementService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
}

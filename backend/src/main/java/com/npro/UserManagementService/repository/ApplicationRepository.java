package com.npro.UserManagementService.repository;

import com.npro.UserManagementService.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}

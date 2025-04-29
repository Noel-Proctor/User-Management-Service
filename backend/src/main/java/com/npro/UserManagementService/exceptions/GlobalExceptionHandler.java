package com.npro.UserManagementService.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccessDeniedRuntimeException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedRuntimeException(AccessDeniedRuntimeException e) {
        return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
    }




}

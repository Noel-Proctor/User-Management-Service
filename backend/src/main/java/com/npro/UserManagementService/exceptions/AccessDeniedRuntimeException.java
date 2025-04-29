package com.npro.UserManagementService.exceptions;

import java.io.Serial;

public class AccessDeniedRuntimeException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;
  public AccessDeniedRuntimeException(String message) {
    super(message);
  }
}

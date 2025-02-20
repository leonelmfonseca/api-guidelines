package com.api.guidelines.exceptions;

public class DatabaseOperationException extends RuntimeException {
  public DatabaseOperationException(String message) {
    super(message);
  }
}

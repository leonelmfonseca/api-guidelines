package com.api.guidelines.aop;

import com.api.guidelines.exceptions.ProductErrorResponse;
import com.api.guidelines.exceptions.ProductInvalidIdException;
import com.api.guidelines.exceptions.ProductNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/*
 Advantages of using @RestControllerAdvice annotation:
 1) Centralized Logic: All exceptions are handled in one place, making the code cleaner and easier to maintain.
 2) Consistency: Ensures that all error responses follow the same structure and include proper HTTP status codes.
 3) Separation of Concerns: Keeps exception handling logic separate from business logic and API documentation.
*/
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ProductErrorResponse> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {

    String message = "Invalid value for '" + ex.getName() + "'";
    ProductErrorResponse errorResponse =
        new ProductErrorResponse(
            HttpStatus.BAD_REQUEST.value(), message, System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ProductErrorResponse> handleProductNotFoundException(
      ProductNotFoundException ex) {
    ProductErrorResponse errorResponse =
        new ProductErrorResponse(
            HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ProductInvalidIdException.class)
  public ProductErrorResponse handleInvalidIdException(ProductInvalidIdException ex) {
    return new ProductErrorResponse(
        HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ProductErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex) {

    // Extract the first validation error message
    String message =
        ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage) // Use the custom message from the annotation
            .findFirst()
            .orElse("Validation failed");

    ProductErrorResponse errorResponse =
        new ProductErrorResponse(
            HttpStatus.BAD_REQUEST.value(), message, System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}

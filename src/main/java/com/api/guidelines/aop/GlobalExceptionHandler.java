package com.api.guidelines.aop;

import static com.api.guidelines.utils.ProductUtils.createProductResponse;

import com.api.guidelines.exceptions.DatabaseOperationException;
import com.api.guidelines.exceptions.ProductAlreadyExistsException;
import com.api.guidelines.exceptions.ProductInvalidIdException;
import com.api.guidelines.exceptions.ProductNotFoundException;
import com.api.guidelines.exceptions.ProductResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
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
  public @NotNull ResponseEntity<ProductResponse> handleMethodArgumentTypeMismatch(
      @NotNull MethodArgumentTypeMismatchException ex) {

    ProductResponse errorResponse =
        createProductResponse(HttpStatus.BAD_REQUEST, "Invalid request");
    return new ResponseEntity<>(errorResponse, errorResponse.status());
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public @NotNull ResponseEntity<ProductResponse> handleProductNotFoundException(
      @NotNull ProductNotFoundException ex) {

    ProductResponse errorResponse = createProductResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    return new ResponseEntity<>(errorResponse, errorResponse.status());
  }

  @ExceptionHandler(ProductInvalidIdException.class)
  public @NotNull ResponseEntity<ProductResponse> handleInvalidIdException(
      @NotNull ProductInvalidIdException ex) {
    ProductResponse errorResponse = createProductResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(errorResponse, errorResponse.status());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public @NotNull ResponseEntity<ProductResponse> handleConstraintViolationException(
      @NotNull ConstraintViolationException ex) {

    // Extract the first validation error message
    String message =
        ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage) // Use the custom message from the annotation
            .findFirst()
            .orElse("Validation failed");

    ProductResponse errorResponse =
        new ProductResponse(HttpStatus.BAD_REQUEST, message, System.currentTimeMillis());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ProductAlreadyExistsException.class)
  public @NotNull ResponseEntity<ProductResponse> handleProductAlreadyExistsException(
      @NotNull ProductAlreadyExistsException ex) {
    ProductResponse errorResponse = createProductResponse(HttpStatus.CONFLICT, ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(DatabaseOperationException.class)
  public @NotNull ResponseEntity<ProductResponse> handleDatabaseOperationException(
      @NotNull ProductAlreadyExistsException ex) {
    ProductResponse errorResponse =
        createProductResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

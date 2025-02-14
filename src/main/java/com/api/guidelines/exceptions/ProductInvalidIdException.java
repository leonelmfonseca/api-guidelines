package com.api.guidelines.exceptions;

public class ProductInvalidIdException extends RuntimeException {
  public ProductInvalidIdException() {
    super("Product id must be a positive integer");
  }
}

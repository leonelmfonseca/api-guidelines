package com.api.guidelines.exceptions;

import com.api.guidelines.dto.ProductDTO;

public class ProductAlreadyExistsException extends RuntimeException {
  public ProductAlreadyExistsException(ProductDTO productDTO) {
    super("Product " + productDTO.toString() + " already exists");
  }
}

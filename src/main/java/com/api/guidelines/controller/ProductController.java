package com.api.guidelines.controller;

import com.api.guidelines.exceptions.ProductInvalidIdException;
import com.api.guidelines.exceptions.ProductNotFoundException;
import com.api.guidelines.model.Product;
import com.api.guidelines.service.ProductService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Validated // method level validation
@RequestMapping("/api/products")
public class ProductController {

  ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{id}")
  public Optional<Product> findProductById(
      @PathVariable @Min(value = 1, message = "Product ID must be at least 1") Long id) {
    validateProductId(id);
    return Optional.ofNullable(
        productService.findProductById(id).orElseThrow(() -> new ProductNotFoundException(id)));
  }

  private static void validateProductId(Long id) {
    if (id < 1) {
      throw new ProductInvalidIdException();
    }
  }
}

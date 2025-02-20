package com.api.guidelines.controller;

import com.api.guidelines.dto.ProductDTO;
import com.api.guidelines.exceptions.ProductNotFoundException;
import com.api.guidelines.service.ProductService;
import jakarta.validation.constraints.Min;
import java.util.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated // method level validation
@RequestMapping("/api/products") // By default @RequestMapping maps all HTTP operations.
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{id}")
  public Optional<ProductDTO> findProductById(
      @PathVariable @Min(value = 1, message = "Product ID must be at least 1") Long id) {

    return Optional.ofNullable(
        productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException(id)));
  }

  @GetMapping
  public List<ProductDTO> findAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductDTO productDTO) {
    if (productService.isProductExisting(
        productDTO.getName(), productDTO.getDescription(), productDTO.getCategory())) {
      Map<String, Object> response = getStringObjectMap(productDTO);
      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    ProductDTO createdProductDTO = productService.createProduct(productDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("product", createdProductDTO));
  }

  private static @NotNull Map<String, Object> getStringObjectMap(ProductDTO productDTO) {
    // optimize: use ProductErrorResponse instead
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Product already exists");
    response.put("product", productDTO.getName());
    response.put("timestamp", System.currentTimeMillis());
    return response;
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDTO> updateProduct(
      @NotNull @PathVariable Long id, @NotNull @RequestBody ProductDTO productDTO) {
    ProductDTO updatedProductDTO = productService.modifyProduct(id, productDTO);
    if (updatedProductDTO != null) {
      return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }
    throw new ProductNotFoundException(id);
  }

  @GetMapping("/deleteProduct")
  public ResponseEntity<Map<String, Object>> deleteProductByIdAntiPattern(
      @NotNull @RequestParam Long id) {

    productService.removeProductById(id);
    // optimize: use ProductErrorResponse instead
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Product deleted successfully using anti-pattern implementation :(");
    response.put("deletedProductId", id);
    response.put("timestamp", System.currentTimeMillis());

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteProductById(@NotNull @PathVariable Long id) {
    productService.removeProductById(id);
    // optimize: use ProductErrorResponse instead
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Product deleted successfully using best practices implementation");
    response.put("deletedProductId", id);
    response.put("timestamp", System.currentTimeMillis());
    return ResponseEntity.ok(response);
  }
}

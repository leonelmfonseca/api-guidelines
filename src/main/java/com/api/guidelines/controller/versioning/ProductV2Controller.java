package com.api.guidelines.controller.versioning;

import com.api.guidelines.model.Product;
import com.api.guidelines.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// API versioning in Java is a crucial best practice for
// maintaining backward compatibility and ensuring a smooth transition
// between different versions of your API.
@RestController
@RequestMapping("/api/v2/products")
/*
 The RestControllerAdvice annotation is suitable for Spring Boot applications serving RESTful services
 where the controllers return data in the form of JSON or other serialization formats
 */
@RestControllerAdvice
public class ProductV2Controller {

  ProductService productService;

  @Autowired
  public ProductV2Controller(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> findAllV2() {
    return productService.findAllV2();
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Product> findProductById(@PathVariable Long id) {
   
    Optional<Product> product = productService.findProductById(id);
    
    return product.map(ResponseEntity :: ok) // Return 200 with product
            .orElse(ResponseEntity.notFound().build()); // Return 404 if not found
  }
}

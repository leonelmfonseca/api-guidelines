package com.api.guidelines.controller;

import com.api.guidelines.model.Product;
import com.api.guidelines.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// API versioning in Java is a crucial best practice for
// maintaining backward compatibility and ensuring a smooth transition
// between different versions of your API.
@RestController
@RequestMapping("/api/v2/products")
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
}

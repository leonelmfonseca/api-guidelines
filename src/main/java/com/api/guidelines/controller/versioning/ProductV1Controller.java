package com.api.guidelines.controller.versioning;

import com.api.guidelines.dto.ProductDTO;
import com.api.guidelines.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
API versioning in Java is a crucial best practice for maintaining backward compatibility
and ensuring a smooth transition.
API versioning can be implementing using URL segments, query parameters, or headers to ensure
backward compatibility and smooth transitions between versions
*/

// API versioning in Java is a crucial best practice for
// maintaining backward compatibility and ensuring a smooth transition
// between different versions of your API.
@RestController
@RequestMapping("/api/v1/products")
public class ProductV1Controller {

  private final ProductService productService;

  @Autowired
  public ProductV1Controller(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<ProductDTO> findAllV1() {
    return productService.getAllProductsV1();
  }
}

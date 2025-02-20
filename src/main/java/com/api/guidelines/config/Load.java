package com.api.guidelines.config;

import com.api.guidelines.dto.ProductDTO;
import com.api.guidelines.service.ProductService;
import jakarta.validation.constraints.NotNull;
import java.util.logging.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Load {

  private final Logger logger = Logger.getLogger(getClass().getName());

  @Bean
  @NotNull
  CommandLineRunner loadSample(@NotNull ProductService productService) {
    return args -> {
      insertProduct(
          productService,
          "Laptop",
          999.99,
          "High-performance laptop with 16GB RAM",
          "Electronics",
          50);
      insertProduct(
          productService,
          "Smartphone",
          699.99,
          "Latest smartphone with 128GB storage",
          "Electronics",
          100);
      insertProduct(
          productService,
          "Headphones",
          149.99,
          "Noise-cancelling over-ear headphones",
          "Accessories",
          200);
      insertProduct(productService, "Coffee Maker", 79.99, "", "", 0);
      insertProduct(productService, "Backpack", 49.99, "", "", 0);
      insertProduct(productService, "Tablet", 249.99, "", "", 0);
    };
  }

  private void insertProduct(
      @NotNull ProductService productService,
      String name,
      double price,
      String description,
      String category,
      int stock) {
    try {
      productService.createProduct(new ProductDTO(null, name, price, description, category, stock));
    } catch (RuntimeException e) {
      logger.info("Error inserting product: " + e.getMessage());
    }
  }
}

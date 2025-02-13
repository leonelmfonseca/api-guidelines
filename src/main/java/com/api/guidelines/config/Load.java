package com.api.guidelines.config;

import com.api.guidelines.model.Product;
import com.api.guidelines.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Load {
    
    @Bean
    CommandLineRunner loadSample(ProductService productService) {
        return args -> {
            
            productService.addProduct(new Product(1L, "Laptop", 999.99, "High-performance laptop with 16GB RAM", "Electronics", 50));
            productService.addProduct(new Product(2L, "Smartphone", 699.99, "Latest smartphone with 128GB storage", "Electronics", 100));
            productService.addProduct(new Product(3L, "Headphones", 149.99, "Noise-cancelling over-ear headphones", "Accessories", 200));
            productService.addProduct(new Product(4L, "Coffee Maker", 79.99));
            productService.addProduct(new Product(5L, "Backpack", 49.99));
            productService.addProduct(new Product(6L, "Tablet", 249.99));
            
        };
    }

}

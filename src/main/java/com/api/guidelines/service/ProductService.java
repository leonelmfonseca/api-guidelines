package com.api.guidelines.service;

import com.api.guidelines.model.Product;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class ProductService {

  private List<Product> products;

  public List<Product> findAllV1() {
    return products;
  }

  public List<Product> findAllV2() {
    return products.stream().filter(p -> p.getStock() > 0).toList();
  }

  public void addProduct(Product product) {
    products.add(product);
  }

  public Optional<Product> findProductById(Long id) {
    return products.stream().filter(p -> p.getId().equals(id)).findFirst();
  }
}

package com.api.guidelines.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.api.guidelines.entity.Product;
import com.api.guidelines.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class LoadTest {
  @Autowired ProductRepository productRepository;

  @Test
  @Transactional
  void testConcurrentUpdate() {
    Product entity = productRepository.findById(1L).orElseThrow();
    entity.setName("Updated Name");

    // Simulate another transaction updating the same entity
    Product entity2 = productRepository.findById(1L).orElseThrow();
    entity2.setName("Another Update");
    productRepository.save(entity2);

    // This should throw ObjectOptimisticLockingFailureException
    Product savedEntity = productRepository.save(entity);
    assertNotNull(savedEntity);
  }
}

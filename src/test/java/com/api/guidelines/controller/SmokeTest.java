package com.api.guidelines.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

  @Autowired private ProductController productController;

  @Test
  void contextLoads() {
    assertThat(productController).isNotNull();
  }
}

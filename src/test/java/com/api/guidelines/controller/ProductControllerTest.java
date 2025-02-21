package com.api.guidelines.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.guidelines.dto.ProductDTO;
import com.api.guidelines.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductControllerTest.class);
  private static final Faker FAKER = new Faker();
  private static final String PRODUCT_API_URL = "/api/products";

  private Long productId;
  private String productName;
  private Double productPrice;

  @Autowired private MockMvc mockMvc;

  // Don't use @Mock, because it won't integrate with the Spring application context.
  // Instead, Spring Boot uses the actual bean defined in the application context,
  // which leads to your service querying the real database.
  @MockitoBean private ProductService productService;

  private ObjectMapper objectMapper;
  private ProductDTO mockProductDTO;

  @BeforeEach
  void setUp() {
    productId = FAKER.number().numberBetween(1L, 10000L);
    productName = FAKER.commerce().productName();
    productPrice = FAKER.number().randomDouble(2, 500, 2000);
    mockProductDTO = new ProductDTO(productId, productName, productPrice);
    objectMapper = new ObjectMapper();
    LOGGER.debug("Setup mock product: {}", mockProductDTO);
  }

  @Test
  void givenValidId_whenFindProductById_thenReturnProduct() throws Exception {
    // Given
    String uriTemplate = String.format("%s/%d", PRODUCT_API_URL, productId);

    // Stub product service behavior
    when(productService.getProductById(productId)).thenReturn(Optional.of(mockProductDTO));

    // When
    ResultActions resultActions =
        mockMvc.perform(get(uriTemplate).accept(MediaType.APPLICATION_JSON));

    // Then
    resultActions
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(productId))
        .andExpect(jsonPath("$.name").value(productName))
        .andExpect(jsonPath("$.price").value(productPrice))
        .andDo(result -> LOGGER.info("{}", result.getResponse().getContentAsString()));
  }
}

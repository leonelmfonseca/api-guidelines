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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  private static final Long PRODUCT_ID = 1L;
  private static final String PRODUCT_NAME = "Laptop";
  private static final Double PRODUCT_PRICE = 1500D;
  private static final String PRODUCT_API_URL = "/api/products";

  @Autowired private MockMvc mockMvc;

  @Mock private ProductService productService;

  private ObjectMapper objectMapper;
  private ProductDTO mockProductDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    objectMapper = new ObjectMapper();
    mockProductDTO = new ProductDTO(PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE);
  }

  @Test
  void givenValidId_whenFindProductById_thenReturnProduct() throws Exception {
    // Given
    String uriTemplate = String.format("%s/%d", PRODUCT_API_URL, PRODUCT_ID);

    // Stub product service behavior
    when(productService.getProductById(PRODUCT_ID)).thenReturn(Optional.of(mockProductDTO));

    // When
    ResultActions resultActions =
        mockMvc.perform(get(uriTemplate).accept(MediaType.APPLICATION_JSON));

    // Then
    resultActions
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(PRODUCT_ID))
        .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
        .andExpect(jsonPath("$.price").value(PRODUCT_PRICE));
  }
}

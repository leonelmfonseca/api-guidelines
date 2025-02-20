package com.api.guidelines.controller;

import static com.api.guidelines.utils.ProductUtils.createProductResponse;

import com.api.guidelines.dto.ProductDTO;
import com.api.guidelines.exceptions.DatabaseOperationException;
import com.api.guidelines.exceptions.ProductAlreadyExistsException;
import com.api.guidelines.exceptions.ProductNotFoundException;
import com.api.guidelines.exceptions.ProductResponse;
import com.api.guidelines.service.ProductService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated // method level validation
@RequestMapping("/api/products") // By default @RequestMapping maps all HTTP operations.
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{id}")
  public @NotNull Optional<ProductDTO> findProductById(
      @PathVariable @Min(value = 1, message = "Product ID must be at least 1") @NotNull Long id) {

    return Optional.ofNullable(
        productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException(id)));
  }

  @GetMapping
  public List<ProductDTO> findAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping
  public @NotNull ResponseEntity<ProductResponse> createProduct(
      @RequestBody @NotNull ProductDTO productDTO) {

    boolean productExists =
        productService.isProductExisting(
            productDTO.getName(), productDTO.getDescription(), productDTO.getCategory());

    if (productExists) {
      throw new ProductAlreadyExistsException(productDTO);
    }

    ProductDTO createdProductDTO = productService.createProduct(productDTO);
    if (createdProductDTO == null) {
      throw new DatabaseOperationException("Failed to create product in the database.");
    }

    ProductResponse productResponse =
        createProductResponse(
            HttpStatus.CREATED, "Product: " + createdProductDTO + " created successfully.");
    return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public @NotNull ResponseEntity<ProductDTO> updateProduct(
      @NotNull @PathVariable Long id, @NotNull @RequestBody ProductDTO productDTO) {
    ProductDTO updatedProductDTO = productService.modifyProduct(id, productDTO);
    if (updatedProductDTO != null) {
      return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }
    throw new ProductNotFoundException(id);
  }

  @GetMapping("/deleteProduct")
  public @NotNull ResponseEntity<ProductResponse> deleteProductByIdAntiPattern(
      @NotNull @RequestParam Long id) {

    productService.removeProductById(id);

    ProductResponse response =
        createProductResponse(
            HttpStatus.OK,
            "Product: " + id + " deleted successfully,using anti-pattern implementation :(.");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public @NotNull ResponseEntity<ProductResponse> deleteProductById(
      @NotNull @PathVariable Long id) {
    productService.removeProductById(id);

    ProductResponse response =
        createProductResponse(
            HttpStatus.OK,
            "Product: " + id + " deleted successfully using best practices implementation :).");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}

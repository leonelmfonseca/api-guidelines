package com.api.guidelines.service;

import com.api.guidelines.dto.ProductDTO;
import com.api.guidelines.entity.Product;
import com.api.guidelines.exceptions.ProductNotFoundException;
import com.api.guidelines.mapper.ProductMapper;
import com.api.guidelines.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
public class ProductService {

  private ProductMapper productMapper;
  private ProductRepository productRepository;

  @Autowired
  public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
    this.productMapper = productMapper;
    this.productRepository = productRepository;
  }

  /*
  Marking a transaction as read-only is a considered a best practice
  when you're executing queries that do not modify the state of the database,
  as it can improve performance and make it clear that the transaction
  is not going to make changes.
   */
  @Transactional(readOnly = true)
  public List<ProductDTO> getAllProductsV1() {
    List<Product> entities = productRepository.findAll();
    return ProductMapper.INSTANCE.toDTOList(entities);
  }

  @Transactional(readOnly = true)
  public List<ProductDTO> getAllProductsV2() {
    List<Product> productEntities = productRepository.findAll();
    List<ProductDTO> productDTOS = ProductMapper.INSTANCE.toDTOList(productEntities);
    Predicate<ProductDTO> isNewProduct = dto -> dto.getStock() > 0;
    return productDTOS.stream().filter(isNewProduct).toList();
  }

  @Transactional(readOnly = true)
  public List<ProductDTO> getAllProducts() {
    List<Product> productEntities = productRepository.findAll();
    return ProductMapper.INSTANCE.toDTOList(productEntities);
  }

  @Transactional
  public ProductDTO createProduct(ProductDTO productDTO) {

    Product savedProduct = productRepository.save(productMapper.toEntity(productDTO));
    return productMapper.toDTO(savedProduct);
  }

  @Transactional(readOnly = true)
  public Optional<ProductDTO> getProductById(Long id) {
    return productRepository.findById(id).map(productMapper::toDTO);
  }

  @Transactional
  public ProductDTO modifyProduct(Long id, ProductDTO productDTO) {
    // ProductNotFoundException extends RuntimeException, which means it's an unchecked exception.
    // And by default, @Transactional will roll back the transaction when an unchecked exception is
    // thrown.
    return productRepository
        .findById(id)
        .map(
            productEntity -> {
              productMapper.updateProductEntity(productEntity, productDTO);
              Product savedEntity = productRepository.save(productEntity);
              return productMapper.toDTO(savedEntity);
            })
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @Transactional
  public void removeProductById(Long productId) {
    if (!productRepository.existsById(productId)) {
      // ProductNotFoundException extends RuntimeException, which means it's an unchecked exception.
      // And by default, @Transactional will roll back the transaction when an unchecked exception
      // is thrown.
      throw new ProductNotFoundException(productId);
    }
    productRepository.deleteById(productId);
  }

  public boolean isProductExisting(String name, String description, String category) {
    return productRepository.existsByNameAndDescriptionAndCategory(name, description, category);
  }

  public List<Product> findByNameAndDescriptionAndCategory(Product product) {
    return this.productRepository.findByNameAndDescriptionAndCategory(
        product.getName(), product.getDescription(), product.getCategory());
  }
}

package com.api.guidelines.mapper;

import com.api.guidelines.dto.ProductDTO;
import com.api.guidelines.entity.Product;
import java.util.List;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  // Convert a single Product to ProductDTO
  ProductDTO toDTO(Product entity);

  // Convert a single ProductDTO to Product
  Product toEntity(ProductDTO dto);

  // Convert a List<Product> to List<ProductDTO>
  List<ProductDTO> toDTOList(List<Product> entities);

  // Convert a List<ProductDTO> to List<Product>
  List<Product> toEntityList(List<ProductDTO> dtos);

  @Mapping(target = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateProductEntity(@MappingTarget Product product, ProductDTO productDTO);
}

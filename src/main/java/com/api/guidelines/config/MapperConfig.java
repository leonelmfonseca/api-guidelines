package com.api.guidelines.config;

import com.api.guidelines.mapper.ProductMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
  @Bean
  public ProductMapper productMapper() {
    return Mappers.getMapper(ProductMapper.class);
  }
}

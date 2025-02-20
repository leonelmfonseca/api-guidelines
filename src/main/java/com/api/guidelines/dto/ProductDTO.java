package com.api.guidelines.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
// With this lombok annotation Equals and Hashcode
// are now determined in "of" fields
@EqualsAndHashCode(of = {"name", "description", "category"})
public class ProductDTO {
  @Positive private Long id;
  @NotEmpty private String name;
  @NotEmpty private double price;
  private String description;
  private String category;
  private int stock;

  public ProductDTO(Long id, String name, double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }
}

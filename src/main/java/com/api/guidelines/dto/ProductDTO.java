package com.api.guidelines.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// With this lombok annotation Equals and Hashcode
// are now determined in "of" fields
@EqualsAndHashCode(of = {"name", "description", "category"})
public class ProductDTO {
  private Long id;
  private String name;
  private double price;
  private String description;
  private String category;
  private int stock;
}

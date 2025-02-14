package com.api.guidelines.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductErrorResponse {
  private int status;
  private String message;
  private long timeStamp;
}

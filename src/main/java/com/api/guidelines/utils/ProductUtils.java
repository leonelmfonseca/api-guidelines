package com.api.guidelines.utils;

import com.api.guidelines.exceptions.ProductResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatusCode;

public class ProductUtils {

  public static @NotNull ProductResponse createProductResponse(
      HttpStatusCode status, String message) {
    return new ProductResponse(status, message, System.currentTimeMillis());
  }
}

package com.api.guidelines.exceptions;

import org.springframework.http.HttpStatusCode;

// Records (Java 17) are immutable by default, concise, and ideal for data carriers like error
// responses
// Auto-generates getters (e.g., message() instead of getMessage()), toString, equals, and hashCode.

public record ProductResponse(HttpStatusCode status, String message, long timeStamp) {
  public ProductResponse {
    if (message == null || message.isBlank()) {
      throw new IllegalArgumentException("Message cannot be null or blank");
    }
  }
}

package com.api.guidelines.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  // To match all subpaths under the specified endpoints use "/**"
  private static final String[] PUBLIC_ENDPOINTS = {
    "/api/v1/products/**", "/api/v2/products/**", "/api/products/**", "/h2-console/**"
  };

  @Bean
  // todo: create custom exception to avoid throwing a generic exception
  public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
    http // Disable CSRF (Cross-Site Request Forgery) for stateless APIs
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth -> auth.requestMatchers(PUBLIC_ENDPOINTS).permitAll().anyRequest().authenticated())
        .httpBasic(withDefaults())
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

    return http.build();
  }
}

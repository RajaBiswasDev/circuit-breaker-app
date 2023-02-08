package com.circuitbreaker.demo;

import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
  @Value("${recommendingAppUrl}")
  public String recommendingAppUrl;
  private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

  private final RestTemplate restTemplate;

  public String   list() {
    CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
    return circuitBreaker.run(() -> restTemplate.getForObject(recommendingAppUrl, String.class),
        this::getDefaultBookList);
  }

  private String getDefaultBookList(Throwable throwable) {
    try {
      LOGGER.error("fallback executed due to : " + throwable.getClass() + ", detail: " + throwable.getMessage());
      return new String(
          Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("fallback-list.json").toURI())));
    } catch (Exception e) {
      LOGGER.error("error occurred while reading the file", e);
    }
    return null;
  }
}

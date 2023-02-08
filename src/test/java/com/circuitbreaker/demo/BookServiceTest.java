package com.circuitbreaker.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  @InjectMocks
  BookService bookService;

  @Mock
  private Resilience4JCircuitBreakerFactory circuitBreakerFactory;
  @Mock
  private Resilience4JCircuitBreaker circuitBreaker;

  @Test
  void list() {
    when(circuitBreakerFactory.create(any())).thenReturn(circuitBreaker);
    when(circuitBreaker.run(any(), any())).thenReturn("books");
    assertNotNull(bookService.list());
  }
}
package com.circuitbreaker.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

  @InjectMocks
  BookController bookController;

  @Mock
  BookService service;

  @Test
  void list() {
    when(service.list()).thenReturn("books");
    assertNotNull(bookController.list());
  }
}
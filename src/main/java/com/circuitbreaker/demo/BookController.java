package com.circuitbreaker.demo;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
  private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

  private final BookService service;

  @GetMapping("/list")
  public String list() {
    String bookList = service.list();

    LOGGER.info("books fetched");
    return bookList;
  }
}

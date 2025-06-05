package com.example.projectreactorexample.controllers;

import com.example.projectreactorexample.model.Book;
import com.example.projectreactorexample.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @PostMapping("/books")
  public Flux<Book> saveBook(@RequestBody List<Book> books) {
    return Flux.fromIterable(books)
        .flatMap(bookService::saveBook);
  }

  @PostMapping("/book")
  public Mono<Book> saveBook(@RequestBody Book book) {
    return bookService.saveBook(book);
  }

  @GetMapping("/books/search")
  public Flux<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/book/{id}")
  public Mono<Book> getBook(@PathVariable UUID id) {
    return bookService.findById(id);
  }

  @DeleteMapping("/book/{id}")
  public Mono<Void> deleteBook(@PathVariable UUID id) {
    return bookService.deleteBook(id);
  }
}


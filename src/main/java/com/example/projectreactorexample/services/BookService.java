package com.example.projectreactorexample.services;

import com.example.projectreactorexample.model.Book;
import com.example.projectreactorexample.repository.BookRepoR2DBC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepoR2DBC bookRepoR2DBC;

  public Flux<Book> saveBooks(List<Book> books) {
    return bookRepoR2DBC.saveAll(books);
  }

 public Mono<Book> saveBook(Book book) {
   return bookRepoR2DBC.save(book);
 }

 public Mono<Book> findById(UUID id) {
   return bookRepoR2DBC.findById(id);
 }

  public Flux<Book> getAllBooks() {
   return bookRepoR2DBC.findAll();
  }

  public Mono<Void> deleteBook(Book book) {
   return bookRepoR2DBC.delete(book);
  }

  public Mono<Void> deleteBook(UUID id) {
    return bookRepoR2DBC.deleteById(id);
  }
}

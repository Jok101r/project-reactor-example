package com.example.projectreactorexample.services;

import com.example.projectreactorexample.model.Book;
import com.example.projectreactorexample.repository.BookRepoR2DBC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class BookServiceTest {
  @Autowired
  private BookService bookService;

  @Autowired
  private BookRepoR2DBC bookRepoR2DBC;

  @Test
  public void createBook_success() {
    Book book = new Book();
    book.setAuthor("Hardy");
    book.setTitle("Simple dimple");

    StepVerifier.create(bookService.saveBook(book))
        .expectNext(book)
        .verifyComplete();
  }

  @Test
  public void deleteBook_success() {
    Book book = new Book();
    book.setAuthor("Hardy");
    book.setTitle("Simple dimple");

    UUID id = bookService.saveBook(book).block().getId();

    StepVerifier.create(bookService.deleteBook(id))
        .expectNextCount(0)
        .verifyComplete();
  }

  @Test
  public void findAllBook_success() {
    List<Book> books = generateBooks();

    StepVerifier.create(bookService.saveBooks(books))
        .expectSubscription()
        .thenRequest(3)
        .expectNext(books.get(0), books.get(1), books.get(2))
        .thenRequest(2)
        .expectNext(books.get(3), books.get(4))
        .verifyComplete();
  }

  private List<Book> generateBooks(){
    List<Book> books = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Book book = new Book();
      book.setAuthor("Jordan_"+ UUID.randomUUID());
      book.setTitle("basketball_"+ UUID.randomUUID());
      books.add(book);
    }
    return books;
  }

  @AfterEach
  public void cleanUp() {
    bookRepoR2DBC.deleteAll().block();
  }
}

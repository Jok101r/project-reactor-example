package com.example.projectreactorexample.controllers;

import com.example.projectreactorexample.model.Book;
import com.example.projectreactorexample.repository.BookRepoR2DBC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class BookControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private BookRepoR2DBC bookRepoR2DBC;

  @BeforeEach
  public void setUp() {
    for (int i = 0; i < 10; i++) {
      webTestClient.post()
          .uri("/book")
          .bodyValue(generateBooks())
          .exchange()
          .expectStatus().isOk();
    }
  }

  @Test
  public void createBook_successful() {
    Book book = new Book();
    book.setAuthor("Hardy");
    book.setTitle("Simple dimple");

    webTestClient.post()
        .uri("/book")
        .bodyValue(book)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isNotEmpty()
        .jsonPath("$.author").isEqualTo(book.getAuthor())
        .jsonPath("$.title").isEqualTo(book.getTitle());
  }

  @Test
  public void findAllBook_successful() {
    webTestClient.get()
        .uri("/books/search")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Book.class)
        .hasSize(10);
  }

  @Test
  public void deleteBook_successful() {
    List<Book> books = webTestClient.get()
        .uri("/books/search")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Book.class)
        .returnResult().getResponseBody();

    webTestClient.delete()
        .uri("/book/{id}", books.getFirst().getId())
        .exchange()
        .expectStatus().isOk();

    webTestClient.delete()
        .uri("/book/{id}", books.getLast().getId())
        .exchange()
        .expectStatus().isOk();

    webTestClient.get()
        .uri("/books/search")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Book.class)
        .hasSize(8);

    webTestClient.get()
        .uri("/book/{id}", books.getFirst().getId())
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .isEmpty();

    webTestClient.get()
        .uri("/book/{id}", books.getLast().getId())
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .isEmpty();
  }

  @AfterEach
  public void cleanUp() {
    bookRepoR2DBC.deleteAll().block();
  }

  private Book generateBooks() {
    Book book = new Book();
    book.setAuthor("Shardy_test" + UUID.randomUUID());
    book.setTitle("Tolkien" + UUID.randomUUID());
    return book;
  }
}

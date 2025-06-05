package com.example.projectreactorexample.controllers;

import com.example.projectreactorexample.controllers.dto.OrderDTO;
import com.example.projectreactorexample.model.Book;
import com.example.projectreactorexample.model.Magazine;
import com.example.projectreactorexample.repository.BookRepoR2DBC;
import com.example.projectreactorexample.repository.MagazineRepoR2DBC;
import com.example.projectreactorexample.repository.OrderRepoR2DBC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureWebTestClient
public class OrderControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private MagazineRepoR2DBC magazineRepoR2DBC;

  @Autowired
  private BookRepoR2DBC bookRepoR2DBC;

  @Autowired
  private OrderRepoR2DBC orderRepoR2DBC;

  @BeforeEach
  public void setUp() {


//    List<Order> orders = new ArrayList<>();
//    for (int i = 0; i < 10; i++) {
//      orders.add(generatedOrder());
//    }
//    webTestClient.post()
//        .uri("/orders")
//        .bodyValue(orders)
//        .exchange()
//        .expectStatus().isOk();
  }

  @Test
  public void createOrder_successful() {

    List<OrderDTO> orders = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      orders.add(generatedOrder());
    }
    String ss = webTestClient.post()
        .uri("/orders")
        .bodyValue(orders)
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
        .returnResult().getResponseBody();
    System.out.println(ss);

  }


  private OrderDTO generatedOrder() {
    Book book = new Book();
    book.setTitle("Book Title_" + UUID.randomUUID());
    book.setAuthor("Author_" + UUID.randomUUID());

    Magazine magazine = new Magazine();
    magazine.setTitle("Magazine_" + UUID.randomUUID());
    magazine.setAuthor("Author_" + UUID.randomUUID());

    OrderDTO order = new OrderDTO();
    order.setBook(book);
    order.setMagazine(magazine);
    return order;
  }

  @AfterEach
  public void cleanUp() {
    magazineRepoR2DBC.deleteAll().block();
    bookRepoR2DBC.deleteAll().block();
    orderRepoR2DBC.deleteAll().block();
  }
}

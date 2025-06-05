package com.example.projectreactorexample.services;

import com.example.projectreactorexample.controllers.dto.OrderDTO;
import com.example.projectreactorexample.model.Book;
import com.example.projectreactorexample.model.Magazine;
import com.example.projectreactorexample.model.Order;
import com.example.projectreactorexample.repository.OrderRepoR2DBC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  private final OrderRepoR2DBC orderRepoR2DBC;
  private final BookService bookService;
  private final MagazineService magazineService;

  public Mono<Order> saveOrder(Order order) {
    return orderRepoR2DBC.save(order);
  }

  public Mono<Order> findById(UUID id) {
    return orderRepoR2DBC.findById(id);
  }
  public Flux<Order> createOrders(List<OrderDTO> orders) {
    Flux<Book> bookFlux = bookService.saveBooks(orders.stream().map(OrderDTO::getBook).toList())
        .doOnNext(book -> log.info("Book id is {}", book.getId()))
        .doOnNext(book -> {
          book.setCreateFromOrder(true);
        });
    Flux<Magazine> magazineFlux = magazineService.saveMagazines(orders.stream().map(OrderDTO::getMagazine).toList())
        .doOnNext(magazine -> log.info("Magazine id is {}", magazine.getId()))
        .doOnNext(magazine -> {
          magazine.setCreateFromOrder(true);
        });

    return Flux.zip(bookFlux, magazineFlux, (book, magazine) -> {
          log.info("Book id is {}", book.getId());
          log.info("Magazine id is {}", magazine.getId());
          Order order = new Order();
          order.setBookId(book.getId());
          order.setMagazineId(magazine.getId());
          return order;
        })
        .flatMap(this::saveOrder);
  }
}


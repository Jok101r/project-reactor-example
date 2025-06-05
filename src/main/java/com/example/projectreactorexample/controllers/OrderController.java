package com.example.projectreactorexample.controllers;

import com.example.projectreactorexample.controllers.dto.OrderDTO;
import com.example.projectreactorexample.model.Order;
import com.example.projectreactorexample.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/orders")
  public Flux<Order> createOrder(@RequestBody List<OrderDTO> orders) {
    return orderService.createOrders(orders)
        .log()
        .delayElements(Duration.ofMillis(500));
  }
}


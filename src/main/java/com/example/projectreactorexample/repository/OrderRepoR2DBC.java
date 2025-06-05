package com.example.projectreactorexample.repository;

import com.example.projectreactorexample.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderRepoR2DBC extends ReactiveCrudRepository<Order, UUID> {
}
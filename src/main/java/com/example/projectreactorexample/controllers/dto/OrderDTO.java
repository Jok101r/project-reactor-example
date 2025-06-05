package com.example.projectreactorexample.controllers.dto;

import com.example.projectreactorexample.model.Book;
import com.example.projectreactorexample.model.Magazine;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDTO {
  private UUID id;
  private Book book;
  private Magazine magazine;
}

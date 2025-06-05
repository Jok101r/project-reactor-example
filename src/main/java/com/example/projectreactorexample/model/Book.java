package com.example.projectreactorexample.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("T_BOOK")
public class Book implements Persistable<UUID> {
  @Id
  private UUID id;
  private String title;
  private String author;
  @Transient
  private boolean createFromOrder = false;

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    if (Objects.isNull(id)) {
      id = UUID.randomUUID();
      return true;
    }
    return false;
  }
}

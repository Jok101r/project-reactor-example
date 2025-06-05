package com.example.projectreactorexample.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@ToString
@Table("T_ORDER")
public class Order implements Persistable<UUID> {
  @Id
  private UUID id;
  private UUID bookId;
  private UUID magazineId;

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


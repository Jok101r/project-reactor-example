package com.example.projectreactorexample.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Table("T_MAGAZINE")
public class Magazine implements Persistable<UUID> {
  @Id
  private UUID id;
  private String title;
  private String author;
  @Transient
  private boolean createFromOrder;

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

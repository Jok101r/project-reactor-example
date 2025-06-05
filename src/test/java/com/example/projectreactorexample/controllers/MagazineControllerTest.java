package com.example.projectreactorexample.controllers;

import com.example.projectreactorexample.model.Magazine;
import com.example.projectreactorexample.repository.MagazineRepoR2DBC;
import org.junit.jupiter.api.AfterAll;
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
public class MagazineControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private MagazineRepoR2DBC magazineRepoR2DBC;

  @BeforeEach
  public void setUp() {
    for (int i = 0; i < 10; i++) {
      webTestClient.post()
          .uri("/magazine")
          .bodyValue(generateMagazines())
          .exchange()
          .expectStatus().isOk();
    }
  }

  @Test
  public void createMagazine_successful() {
    Magazine magazine = new Magazine();
    magazine.setAuthor("Ford");
    magazine.setTitle("First step");

    webTestClient.post()
        .uri("/magazine")
        .bodyValue(magazine)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isNotEmpty()
        .jsonPath("$.author").isEqualTo(magazine.getAuthor())
        .jsonPath("$.title").isEqualTo(magazine.getTitle());
  }

  @Test
  public void findAllMagazine_successful() {
    webTestClient.get()
        .uri("/magazines/search")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Magazine.class)
        .hasSize(10);
  }

  @Test
  public void deleteMagazine_successful() {
    List<Magazine> magazines = webTestClient.get()
        .uri("/magazines/search")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Magazine.class)
        .returnResult().getResponseBody();

    webTestClient.delete()
        .uri("/magazine/{id}", magazines.getFirst().getId())
        .exchange()
        .expectStatus().isOk();

    webTestClient.delete()
        .uri("/magazine/{id}", magazines.getLast().getId())
        .exchange()
        .expectStatus().isOk();

    webTestClient.get()
        .uri("/magazines/search")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Magazine.class)
        .hasSize(8);

    webTestClient.get()
        .uri("/magazine/{id}", magazines.getFirst().getId())
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .isEmpty();

    webTestClient.get()
        .uri("/magazine/{id}", magazines.getLast().getId())
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .isEmpty();
  }

  @AfterEach
  public void cleanUp() {
    magazineRepoR2DBC.deleteAll().block();
  }

  private Magazine generateMagazines() {
    Magazine magazine = new Magazine();
    magazine.setAuthor("Lex_test" + UUID.randomUUID());
    magazine.setTitle("Pushkk_" + UUID.randomUUID());
    return magazine;
  }
}

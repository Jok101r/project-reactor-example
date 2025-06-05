package com.example.projectreactorexample.controllers;

import com.example.projectreactorexample.model.Magazine;
import com.example.projectreactorexample.services.MagazineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MagazineController {

  private final MagazineService magazineService;

  @PostMapping("/magazines")
  public Flux<Magazine> saveMagazine(@RequestBody List<Magazine> magazines) {
    return Flux.fromIterable(magazines)
        .flatMap(magazineService::saveMagazine);
  }

  @PostMapping("/magazine")
  public Mono<Magazine> saveBook(@RequestBody Magazine magazine) {
    return magazineService.saveMagazine(magazine);
  }

  @GetMapping("/magazines/search")
  public Flux<Magazine> getAllMagazines() {
    return magazineService.getAllMagazines();
  }

  @GetMapping("/magazine/{id}")
  public Mono<Magazine> getMagazine(@PathVariable UUID id) {
    return magazineService.findById(id);
  }

  @DeleteMapping("/magazine/{id}")
  public Mono<Void> deleteMagazine(@PathVariable UUID id) {
    return magazineService.deleteMagazine(id);
  }
}


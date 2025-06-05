package com.example.projectreactorexample.services;

import com.example.projectreactorexample.model.Magazine;
import com.example.projectreactorexample.repository.MagazineRepoR2DBC;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MagazineService {

  private final MagazineRepoR2DBC magazineRepoR2DBC;

  public Flux<Magazine> saveMagazines(List<Magazine> magazine) {
    return magazineRepoR2DBC.saveAll(magazine);
  }

  public Mono<Magazine> saveMagazine(Magazine magazine) {
    return magazineRepoR2DBC.save(magazine);
  }

  public Mono<Magazine> findById(UUID id) {
    return magazineRepoR2DBC.findById(id);
  }

  public Flux<Magazine> getAllMagazines() {
    return magazineRepoR2DBC.findAll();
  }

  public Mono<Void> deleteMagazine(Magazine magazine) {
    return magazineRepoR2DBC.delete(magazine);
  }

  public Mono<Void> deleteMagazine(UUID id) {
    return magazineRepoR2DBC.deleteById(id);
  }

}

package com.example.projectreactorexample.repository;

import com.example.projectreactorexample.model.Magazine;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface MagazineRepoR2DBC extends ReactiveCrudRepository<Magazine, UUID> {

}
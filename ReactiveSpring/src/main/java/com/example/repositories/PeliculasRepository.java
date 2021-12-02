package com.example.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.domains.entities.Peliculas;

public interface PeliculasRepository extends ReactiveMongoRepository<Peliculas, String> {

}

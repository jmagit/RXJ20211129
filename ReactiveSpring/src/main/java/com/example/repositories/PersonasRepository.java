package com.example.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.domains.entities.Persona;

public interface PersonasRepository extends ReactiveMongoRepository<Persona, String> {

}

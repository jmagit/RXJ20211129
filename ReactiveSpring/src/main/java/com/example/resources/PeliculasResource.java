package com.example.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.entities.Peliculas;
import com.example.domains.entities.Persona;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.repositories.PeliculasRepository;
import com.example.repositories.PersonasRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/peliculas")
public class PeliculasResource {
	@Autowired
	PeliculasRepository dao;

	@GetMapping
	public Flux<Peliculas> getAll(@RequestParam(required = true, defaultValue = "0") int page, @RequestParam(required = true, defaultValue = "20") int rows) {
		return dao.findAll().skip(page*rows).take(rows);
		
	}
	
	@GetMapping(path = "/{id}")
	public Mono<Peliculas> getOne(@PathVariable String id) {
		return dao.findById(id).single().onErrorMap(original -> new NotFoundException(original));
		
	}
	@PostMapping
	public Mono<Peliculas> create(@RequestBody Peliculas item) {
		return dao.insert(item).onErrorMap(original -> new InvalidDataException(original.getMessage(), original));
	}
	@PutMapping(path = "/{id}")
	public Mono<Peliculas> update(@Valid @PathVariable String id, @RequestBody Peliculas item) {
		return dao.save(item); //.onErrorMap(original -> new InvalidDataException(original.getMessage(), original));
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable String id) {
		return dao.deleteById(id).switchIfEmpty(Mono.error(new NotFoundException()));
	}

}

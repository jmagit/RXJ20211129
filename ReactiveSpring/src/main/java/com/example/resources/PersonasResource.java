package com.example.resources;

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

import com.example.domains.entities.Persona;
import com.example.repositories.PersonasRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/personas")
public class PersonasResource {
	@Autowired
	PersonasRepository dao;

	@GetMapping
	public Flux<Persona> getAll(@RequestParam(required = true, defaultValue = "0") int page, @RequestParam(required = true, defaultValue = "20") int rows) {
		return dao.findAll().skip(page*rows).take(rows);
		
	}
	
	@GetMapping(path = "/{id}")
	public Mono<Persona> getOne(@PathVariable String id) {
		return dao.findById(id);
		
	}
	@PostMapping
	public Mono<Persona> create(@RequestBody Persona item) {
		return dao.insert(item);
	}
	@PutMapping(path = "/{id}")
	public Mono<Persona> update(@PathVariable String id, @RequestBody Persona item) {
		return dao.save(item);
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable String id) {
		return dao.deleteById(id);
	}

}

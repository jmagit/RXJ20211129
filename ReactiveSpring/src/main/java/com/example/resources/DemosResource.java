package com.example.resources;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.entities.Persona;
import com.example.repositories.PersonasRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/demos")
public class DemosResource {
	@GetMapping("/saluda/{id}")
	public String saluda(@PathVariable String id, @RequestParam(required = false, defaultValue = "Hola") String tipo, 
			@RequestHeader("Accept-Language") String idioma) {
		return tipo + " " + id + " en " + idioma;
	}
	@GetMapping("/dato")
	public Mono<Persona> dato() {
		return Mono.fromSupplier(() -> new Persona("0", "Pepito", "Grillo", 99));
	}
	@GetMapping("/datos")
	public Flux<Persona> datos() {
		return Flux.just(new Persona("0", "Pepito", "Grillo", 99), new Persona("1", "Carmelo", "Coton", 22));
	}
	
	@Autowired
	PersonasRepository dao;
	
	@GetMapping("/personas")
	public Flux<Persona> personas() {
//		dao.findById("61a342e5d0ae660ec24269ce");
//		dao.save( new Persona("0", "Pepito", "Grillo", 99));
//		dao.deleteById("61a342e5d0ae660ec24269ce");
		return dao.findAll();
	}
	@GetMapping("/lento")
	public Mono<List<Persona>> lento() {
		return dao.findAll().collectList();
	}
	@GetMapping("/lento/{id}")
	public Mono<Persona> lento(@PathVariable String id) {
		return dao.findById(id).delayElement(Duration.ofSeconds(3));
	}
	@GetMapping("/rapido/{id}")
	public Mono<Persona> rapido(@PathVariable String id) {
		return dao.findById(id);
	}
	
	@GetMapping(path = "/cotilla")
	public Flux<Entry<String,List<String>>> cotilla(ServerHttpRequest request) {
		return Flux.fromIterable(request.getHeaders().entrySet());
	}

}

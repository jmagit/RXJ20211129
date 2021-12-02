package com.example;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ClienteResource {
	@Data @AllArgsConstructor @NoArgsConstructor
	public static class Persona {
		private String id;
		private String nombre;
		private String apellidos;
		private int edad;
	}

	@GetMapping("/rapido")
	Flux<Persona> rapido() {
		WebClient client = WebClient.create("http://localhost:8001");
		return Flux.merge(
				client.get().uri("/personas?page=0").retrieve().bodyToFlux(Persona.class), 
				client.get().uri("/personas?page=1").retrieve().bodyToFlux(Persona.class), 
				client.get().uri("/personas?page=2").retrieve().bodyToFlux(Persona.class)
			);
	}
	@GetMapping("/lento")
	Flux<Persona> lento() {
		RestTemplate rest = new RestTemplate();
		List<Persona> l1 = rest.getForObject("http://localhost:8001/personas?page=0", List.class);
		List<Persona> l2 = rest.getForObject("http://localhost:8001/personas?page=1", List.class);
		List<Persona> l3 = rest.getForObject("http://localhost:8001/personas?page=2", List.class);
		return Flux.merge(Flux.fromIterable(l1), Flux.fromIterable(l2),Flux.fromIterable(l3));
	}
	
	@GetMapping("/masrapido")
	Flux<Persona> masrapido() {
		WebClient client = WebClient.create("http://localhost:8001");
		return Flux.merge(
				client.get().uri("/demos/lento/61a342e5d0ae660ec24269ce").retrieve().bodyToMono(Persona.class), 
				client.get().uri("/demos/lento/61a342e5d0ae660ec24269d3").retrieve().bodyToMono(Persona.class), 
				client.get().uri("/demos/lento/61a342e5d0ae660ec24269da").retrieve().bodyToMono(Persona.class) 
			);
	}
	@GetMapping("/maslento")
	Flux<Persona> maslento() {
		RestTemplate rest = new RestTemplate();
		Persona l1 = rest.getForObject("http://localhost:8001/demos/lento/61a342e5d0ae660ec24269ce", Persona.class);
		Persona l2 = rest.getForObject("http://localhost:8001/demos/lento/61a342e5d0ae660ec24269d3", Persona.class);
		Persona l3 = rest.getForObject("http://localhost:8001/demos/lento/61a342e5d0ae660ec24269da", Persona.class);
		return Flux.just(l1, l2, l3);
	}
	@GetMapping("/cotilla")
	Mono<String> cotilla() {
		return WebClient.create("http://localhost:8001/").get().uri("/demos/cotilla").retrieve().bodyToMono(String.class);
	}
}

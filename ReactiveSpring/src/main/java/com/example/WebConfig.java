package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.domains.entities.Peliculas;
import com.example.domains.entities.Persona;
import com.example.repositories.PeliculasRepository;
import com.example.repositories.PersonasRepository;
import com.example.resources.PersonasHandler;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

	@Bean
	public RouterFunction<?> routerPersonas(PersonasHandler handler) {
		var ruta = RouterFunctions.route().GET("/contacto/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getOne)
				.GET("/contacto", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getAll);
			ruta = ruta.POST("/contacto", RequestPredicates.contentType(MediaType.APPLICATION_JSON), handler::create)
					.PUT("/contacto/{id}", RequestPredicates.contentType(MediaType.APPLICATION_JSON), handler::update)
					.DELETE("/contacto/{id}", handler::delete);
		return ruta.build();
//		return RouterFunctions.route()
//				.GET("/contacto/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getOne)
//				.GET("/contacto", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getAll)
//				.POST("/contacto", RequestPredicates.contentType(MediaType.APPLICATION_JSON), handler::create)
//				.PUT("/contacto/{id}", RequestPredicates.contentType(MediaType.APPLICATION_JSON), handler::update)
//				.DELETE("/contacto/{id}", handler::delete)
//				.build();
	}

//	private RouterFunction<?> generateRouterFunction(String path, ReactiveMongoRepository<?, ?> dao, Class entidad) {
//		return  RouterFunctions.route()
//				.GET(path + "/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), 
//						request -> dao.findById(request.pathVariable("id"))
//							.flatMap(item -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(item))
//							.switchIfEmpty(ServerResponse.notFound().build())
//				).GET(path, RequestPredicates.accept(MediaType.APPLICATION_JSON), 
//						request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(dao.findAll(), entidad)
//				).POST(path, RequestPredicates.contentType(MediaType.APPLICATION_JSON), 
//						request -> request.bodyToMono(Persona.class)
//							.flatMap(item -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(dao.save(item), entidad))			
//				).PUT(path + "/{id}", RequestPredicates.contentType(MediaType.APPLICATION_JSON), 
//						request -> request.bodyToMono(Persona.class)
//							.flatMap(item -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(dao.save(item), entidad))
//				).DELETE(path + "/{id}", 
//						request -> dao.deleteById(request.pathVariable("id")).then(ServerResponse.noContent().build())
//				).build();
//	}
//	
//	@Bean
//	public RouterFunction<?> routerPeliculas(PeliculasRepository dao) {
//		return generateRouterFunction("/peliculas", dao, Peliculas.class);
//	}


}

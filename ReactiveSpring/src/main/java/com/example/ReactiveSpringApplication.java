package com.example;

import java.util.List;
import java.util.concurrent.Flow.Subscription;
import java.util.stream.IntStream;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Mono<String> mono = Mono.just("Adios mundo");
//		mono = Mono.error(() -> new InvalidAttributesException());
//		mono = Mono.never();
////		mono = Mono.empty();
//		System.out.println("no he esperado");
//		mono.subscribe(item -> System.out.print(item), err -> err.printStackTrace(), () -> System.out.println(" y termine"));
//		Flux.just(2,4,6,8,10).subscribe(item -> System.out.println(item));
//		var x = IntStream.range(0, 5).map(item -> Integer.valueOf(item)).toArray();
//		Flux.fromIterable(List.of("uno", "dos", "tres")).subscribe(item -> System.out.println(item));
		
		Flux<String> flux = Flux.generate(() -> 0, (estado, suscriptor) ->{
			suscriptor.next("3 x " + estado + " = " + estado * 3);
			if(estado == 10) suscriptor.complete();
			return estado + 1;
		});

		 imprime(flux);
		// imprime(Flux.empty());
//		imprime(Flux.error(new Exception("Sin datos")));
		//imprime(Flux.never());
	}
	
	public void imprime(Flux<String> flux) {
		class SampleSubscriber<T> extends BaseSubscriber<T> {
			public void hookOnSubscribe(Subscription subscription) {
				System.out.println("Subscribed");
				request(1);
			}
			public void hookOnNext(T value) {
				System.out.println(value);
				request(1);
			}
			@Override
			protected void hookOnError(Throwable err) {
				err.printStackTrace();
				super.hookOnError(err);
			}
		}

//		var suscripcion = flux.subscribe(item -> System.out.println(item), err -> err.printStackTrace());
//		suscripcion.dispose();
		var suscriptor = new SampleSubscriber();
		flux.subscribe(suscriptor);
		suscriptor.cancel();
		System.out.println("termine");
	}

}

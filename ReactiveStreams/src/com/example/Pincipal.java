package com.example;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

public class Pincipal {

	public static void main(String[] args) {
		ejecutar1();
	}

	public static void ejecutar1() {
		SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>();
		var display = new PrintSubscriber();
		var cache = new CacheSubscriber(10);
		var duplica = new GenericProcessor<Integer, Integer>(item -> item * 2);
		
		publisher.subscribe(duplica);
		publisher.subscribe(cache);
		duplica.subscribe(display);
		
		IntStream.range(0, 10).forEach(
				item -> {
					System.out.println("Valor generado: " + item);
					publisher.submit(item);
					Sleeper.sleep(500);
				}
				);
		
		publisher.close();
		System.out.println("Publisher end & close");
		Sleeper.sleep(10000);
		
	}
}

package com.example;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

public class Pincipal {

	public static void main(String[] args) {
		ejecutar3();
	}
	public static void ejecutar4() {
		SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>();
		var display = new PrintSubscriber();
		var cache = new CacheSubscriber(15);
		var duplica = new GenericProcessor<Integer, Integer>(item -> item * 2);
		var pow = new GenericProcessor<Integer, Integer>(item -> item * item);
		var toCadena = new GenericProcessor<Integer, String>(item -> "Valor calculado: " + item);
		boolean enCache=true;
		
		if(enCache)
			publisher.subscribe(cache);
		else {
			publisher.subscribe(toCadena);
			toCadena.subscribe(new PintaCadenaSubscriber());
		}
		
		IntStream.range(0, 10).forEach(
				item -> {
					//System.out.println("Valor generado: " + item);
					publisher.submit(item);
					//Sleeper.sleep(500);
				}
				);
		publisher.submit(100);
		publisher.close();
		System.out.println("Publisher end & close");
		Sleeper.sleep(10000);
		
	}

	public static void ejecutar3() {
		SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>();
		var display = new PrintSubscriber();
		var cache = new CacheSubscriber(15);
		var duplica = new GenericProcessor<Integer, Integer>(item -> item * 2);
		var pow = new GenericProcessor<Integer, Integer>(item -> item * item);
		var toCadena = new GenericProcessor<Integer, String>(item -> "Valor calculado: " + item);
		
		publisher.subscribe(pow);
//		pow.subscribe(duplica);
		pow.subscribe(toCadena);
		toCadena.subscribe(new PintaCadenaSubscriber());
		
		IntStream.range(0, 10).forEach(
				item -> {
					//System.out.println("Valor generado: " + item);
					publisher.submit(item);
					//Sleeper.sleep(500);
				}
				);
		publisher.submit(100);
		publisher.close();
		System.out.println("Publisher end & close");
		Sleeper.sleep(10000);
		
	}

	public static void ejecutar1() {
		SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>();
		var display = new PrintSubscriber();
		var cache = new CacheSubscriber(15);
		var duplica = new GenericProcessor<Integer, Integer>(item -> item * 2);
		var pow = new GenericProcessor<Integer, Integer>(item -> item * item);
		var toCadena = new GenericProcessor<Integer, String>(item -> "Valor calculado: " + item);
		
		publisher.subscribe(duplica);
		duplica.subscribe(pow);
		duplica.subscribe(display);
		pow.subscribe(toCadena);
		toCadena.subscribe(new PintaCadenaSubscriber());
		
		IntStream.range(0, 10).forEach(
				item -> {
					//System.out.println("Valor generado: " + item);
					publisher.submit(item);
					//Sleeper.sleep(500);
				}
				);
		publisher.submit(100);
		publisher.close();
		System.out.println("Publisher end & close");
		Sleeper.sleep(10000);
		
	}
	public static void ejecutar2() {
		SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>();
		var display = new PrintSubscriber();
		var cache = new CacheSubscriber(15);
		var duplica = new GenericProcessor<Integer, Integer>(item -> item * 2);
		
		publisher.subscribe(duplica);
		publisher.subscribe(display);
		duplica.subscribe(cache);
		
		IntStream.range(0, 10).forEach(
				item -> {
					System.out.println("Valor generado: " + item);
					publisher.submit(item);
					if(item == 4) display.cancel();
					if(item == 8) display.continua();
					Sleeper.sleep(500);
				}
				);
		publisher.submit(100);
		publisher.close();
		System.out.println("Publisher end & close");
		Sleeper.sleep(10000);
		
	}
}

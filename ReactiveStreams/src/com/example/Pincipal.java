package com.example;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

import com.example.demos.CacheSubscriber;
import com.example.demos.PintaCadenaSubscriber;
import com.example.demos.PrintSubscriber;
import com.example.domains.NotificationService;
import com.example.domains.entities.Notification;
import com.example.domains.entities.Notification.Types;
import com.example.infrastructure.repository.ProductoRepository;
import com.example.security.AuthService;
import com.example.ui.console.ConsoleDisplay;
import com.example.ui.console.ConsoleNotificationDisplay;
import com.example.ui.web.PageNotificationDisplay;
import com.example.util.FilterProcessor;
import com.example.util.GenericProcessor;
import com.example.util.LoggerRegister;
import com.example.util.Sleeper;

public class Pincipal {

	public static void main(String[] args) {
		notificaciones();
	}
	public static void notificaciones() {
		NotificationService srv = new NotificationService();
		var errores = new FilterProcessor<Notification>(item -> item.getType() == Types.error);
		var cadenas = new GenericProcessor<Notification, String>(item -> item.getMessage());
		srv.subscribe(errores);
		errores.subscribe(cadenas);
		cadenas.subscribe(new LoggerRegister());
		versionConsola(srv);
//		versionWeb(srv);
		srv.pinta = item -> System.out.println(item);
		var auth = new AuthService(srv);
		auth.login(null, null);
		var dao = new ProductoRepository(srv);
		IntStream.range(10, 20).forEach(item -> {
			dao.add(item);
			Sleeper.sleep(0);
			});
		
		System.out.println("Lista de notificaciones");
		srv.getNotifications().forEach(item -> System.out.println(item.getMessage()));
//		System.out.println("Fin");
		Sleeper.sleep(100);
	}
	public static void versionConsola(NotificationService srv) {
		srv.subscribe(new ConsoleNotificationDisplay());
	}
	public static void versionWeb(NotificationService srv) {
		srv.subscribe(new PageNotificationDisplay());
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


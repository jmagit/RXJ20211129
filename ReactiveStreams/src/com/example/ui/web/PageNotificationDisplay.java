package com.example.ui.web;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

import com.example.domains.entities.Notification;
import com.example.util.Sleeper;

public class PageNotificationDisplay implements Flow.Subscriber<Notification> {
	private Flow.Subscription subscription;
	private boolean continuar = true;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Notification item) {
		System.out.printf("<div><h1>%s</h1><p>%s</p></div>\n", item.getType().toString().toUpperCase(), item.getMessage());
		if(continuar) subscription.request(1);
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
	}
	
	public void cancel() {
		continuar = false;
	}
	
	public void continua() {
		continuar = true;
		subscription.request(1);
	}

}

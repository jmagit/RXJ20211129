package com.example;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class PintaCadenaSubscriber implements Flow.Subscriber<String> {
	private Flow.Subscription subscription;
	private boolean continuar = true;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(String item) {
		System.out.println(item);
		if(continuar) subscription.request(1);
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("PintaCadenaSubscriber onComplete");
	}
	
	public void cancel() {
		continuar = false;
		//subscription.cancel();
	}
	
	public void continua() {
		continuar = true;
		subscription.request(1);
	}

}




package com.example;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class PrintSubscriber implements Flow.Subscriber<Integer> {
	private Flow.Subscription subscription;
	private boolean continuar = true;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Integer item) {
		System.out.println("PrintSubscriber: " + item);
		if(continuar) subscription.request(1);
		Sleeper.sleep(1000);
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("PrintSubscriber onComplete");
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

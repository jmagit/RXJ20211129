package com.example;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class PrintSubscriber implements Flow.Subscriber<Integer> {
	private Flow.Subscription subscription;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(Integer item) {
		System.out.println("Print: " + item);
		subscription.request(1);
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

}

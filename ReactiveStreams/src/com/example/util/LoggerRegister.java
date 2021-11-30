package com.example.util;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class LoggerRegister implements Flow.Subscriber<String> {
	private static Logger logger = java.lang.System.getLogger("APP");
	private Flow.Subscription subscription;
	private boolean continuar = true;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(String item) {
		logger.log(Level.ERROR, item);
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
		//subscription.cancel();
	}
	
	public void continua() {
		continuar = true;
		subscription.request(1);
	}

}

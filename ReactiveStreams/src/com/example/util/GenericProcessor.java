package com.example;

import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Flow;

public class GenericProcessor<S, T> extends SubmissionPublisher<T> implements Flow.Processor<S, T> {
	private Flow.Subscription subscription;
	private final Function<S, T> function;
	
	public GenericProcessor(Function<S, T> function) {
		super();
		this.function = function;
	}
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(S item) {
		submit(function.apply(item));
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("GenericProcessor onComplete");
		close();
	}
}

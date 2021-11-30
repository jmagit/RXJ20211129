package com.example.util;

import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Flow;

public class FilterProcessor<T> extends SubmissionPublisher<T> implements Flow.Processor<T, T> {
	private Flow.Subscription subscription;
	private final Predicate<T> delegate;
	
	public FilterProcessor(Predicate<T> delegate) {
		super();
		this.delegate = delegate;
	}
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(T item) {
		if(delegate.test(item)) {
			submit(item);
		}
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
		close();
	}
}

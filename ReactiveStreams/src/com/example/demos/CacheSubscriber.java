package com.example.demos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class CacheSubscriber implements Flow.Subscriber<Integer> {
	private Flow.Subscription subscription;
	private List<Integer> cache = new ArrayList<Integer>();
	private final int MAX_CACHE;
	
	public CacheSubscriber(int maxCache) {
		MAX_CACHE = maxCache;
	}
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(MAX_CACHE);
	}

	@Override
	public void onNext(Integer item) {
		cache.add(item);
		if(cache.size() == MAX_CACHE) pinta();
	}

	@Override
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@Override
	public void onComplete() {
		System.out.println("CacheSubscriber onComplete");
		pinta();
	}
	
	public void pinta() {
		System.out.println("Elementos de la cache");
		for(var item : cache) System.out.println(item);
	}

}

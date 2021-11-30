package com.example.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.example.domains.entities.Notification;

public class NotificationService extends SubmissionPublisher<Notification> {
	private List<Notification> list = new ArrayList<Notification>();
	public Consumer<Notification> pinta;
	public List<Notification> getNotifications() {
		return list; // clonar
	}
	public void add(String message, Notification.Types type) {
		if(message == null || message.isEmpty() || message.isBlank())
			throw new IllegalArgumentException();
		var msg = new Notification(message, type);
		list.add(msg);
		if(pinta != null) pinta.accept(msg);
		this.submit(msg);
	}
	public void add(String message) {
		add(message, Notification.Types.error);
	}
	
	public void remove(int index) {
		if(index < 0 || index >= list.size())
			throw new IndexOutOfBoundsException();
		list.remove(index);
	}
	
	public void clear() {
		list.clear();
	}
	
	public boolean hasNotifications() {
		return list.size() > 0;		
	}
	
	public boolean hasErrors() {
		return list.stream().anyMatch(item -> item.getType() == Notification.Types.error);		
	}
}

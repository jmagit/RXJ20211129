package com.example.infrastructure.repository;

import com.example.domains.NotificationService;
import com.example.domains.entities.Notification.Types;

public class ProductoRepository {
	private NotificationService srv;
	
	public ProductoRepository(NotificationService srv) {
		this.srv = srv;
	}
	public void add(int value) {
		if(value % 3 == 0)
			srv.add("Datos invalidos");
		else 
			srv.add("Añadido " + value, Types.info);
	}
}

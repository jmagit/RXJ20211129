package com.example.security;

import com.example.domains.NotificationService;

public class AuthService {
	private NotificationService srv;
	
	public AuthService(NotificationService srv) {
		this.srv = srv;
	}

	public void login(String usr, String pwd) {
		if(usr == null) {
			srv.add("Usuario o contraseña invalidos");
		}
	}
}

package com.example.domains.entities;

public class Notification {
	public static enum Types {
		error, warn, info, log
	}
	
	private String message;
	private Types type;
	
	public Notification(String message) {
		this.message = message;
		this.type = Types.error;
	}
	public Notification(String message, Types type) {
		this.message = message;
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	public Types getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Notification [type=" + type + ", message=" + message + "]";
	}
}

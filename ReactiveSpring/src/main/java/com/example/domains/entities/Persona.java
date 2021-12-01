package com.example.domains.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Document("personas")
public class Persona {
	@Id
	private String id;
	private String nombre;
	private String apellidos;
	private int edad;
}

package com.example.domains.entities;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Document("personas")
public class Persona {
	@Id
	private String id;
	@NotBlank
	@Length(min = 2, max = 20)
	private String nombre;
	@Length(min = 2, max = 20)
	private String apellidos;
	@Min(16)
	@Max(67)
	private int edad;
}

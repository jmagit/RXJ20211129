package com.example;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.io.Serializable;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ApiExceptionHandler {
	public class ErrorMessage implements Serializable {
		private static final long serialVersionUID = 1L;
		private String error, message;

		public ErrorMessage(String error, String message) {
			this.error = error;
			this.message = message;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	@ExceptionHandler({ NotFoundException.class, NoSuchElementException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Mono<ErrorMessage> notFoundRequest(Exception exception) {
		return Mono.just(new ErrorMessage(exception.getMessage(), ""));
	}

	@ExceptionHandler({ BadRequestException.class, InvalidDataException.class, 
		WebExchangeBindException.class, ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Mono<ErrorMessage> badRequest(Exception exception) {
		return Mono.just(new ErrorMessage(exception.getMessage(), ""));
	}
}

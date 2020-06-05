package com.mth.webquiz.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuizRestExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Validation Error")
	public Map<String, String> handleInvalidParameters(Exception exc) {
		return Map.of(
				"message", "Parametros passados inválidos",
				"error", exc.getClass().getSimpleName()
				);
	}
	
}

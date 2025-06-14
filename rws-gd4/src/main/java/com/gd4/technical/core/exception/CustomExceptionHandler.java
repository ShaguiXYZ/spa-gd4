package com.gd4.technical.core.exception;

import org.apache.catalina.mapper.Mapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for the application.
 * This class intercepts exceptions thrown by controllers and provides
 * appropriate responses to the client.
 */
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({ Exception.class, RuntimeException.class })
	ResponseEntity<?> exception(Exception ex) {
		return buildResponseEntity(ex);
	}

	@ExceptionHandler(FeignException.class)
	ResponseEntity<?> exception(FeignException ex) {
		return buildResponseEntity(ex);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	ResponseEntity<?> exception(IllegalArgumentException ex) {
		return buildResponseEntity(ex);
	}

	private <T extends Exception> ResponseEntity<?> buildResponseEntity(T error) {
		log.error("Exception occurred: {}", error.getMessage(), error);

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(new HttpHeaders())
				.body(error.getMessage());
	}
}

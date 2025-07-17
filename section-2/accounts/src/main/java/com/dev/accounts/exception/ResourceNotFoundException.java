package com.dev.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String resource, String field, String value) {
		super(String.format("%s not found by given %s: '%s'", resource, field, value));
	}

}

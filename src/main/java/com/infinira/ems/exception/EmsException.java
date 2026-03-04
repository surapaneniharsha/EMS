package com.infinira.ems.exception;

public class EmsException extends RuntimeException {
	public EmsException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
}
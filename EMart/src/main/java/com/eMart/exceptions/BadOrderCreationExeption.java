package com.eMart.exceptions;

/**
 * Created by maharshigor on 13/07/16.
 */
public class BadOrderCreationExeption extends Exception {
	public BadOrderCreationExeption() {
	}

	public BadOrderCreationExeption(String message) {
		super (message);
	}

	public BadOrderCreationExeption(String message, Throwable cause) {
		super (message, cause);
	}

	public BadOrderCreationExeption(Throwable cause) {
		super (cause);
	}

	public BadOrderCreationExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super (message, cause, enableSuppression, writableStackTrace);
	}
}

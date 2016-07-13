package com.eMart.exceptions;

/**
 * Created by maharshigor on 12/07/16.
 */
public class IllegalOrderModificationException extends Exception {

	public IllegalOrderModificationException() {
	}

	public IllegalOrderModificationException(String message) {
		super (message);
	}

	public IllegalOrderModificationException(String message, Throwable cause) {
		super (message, cause);
	}

	public IllegalOrderModificationException(Throwable cause) {
		super (cause);
	}

	public IllegalOrderModificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super (message, cause, enableSuppression, writableStackTrace);
	}
}

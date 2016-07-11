package com.eMart.exceptions;

/**
 * Created by maharshigor on 11/07/16.
 */
public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException(String str) {
		super(str);
	}

	public ProductNotFoundException(Exception e) {
		super((e));
	}
}

package com.eMart.model;

/**
 * Created by maharshigor on 09/07/16.
 */
public enum PaymentStatus {
	RECIEVED,
	NOT_RECIEVED;

	public static final PaymentStatus getDefaultStatus() {
		return PaymentStatus.NOT_RECIEVED;
	}
}

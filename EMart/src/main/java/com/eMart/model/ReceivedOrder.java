package com.eMart.model;

import java.util.List;

/**
 * Created by maharshigor on 09/07/16.
 */
public class ReceivedOrder {

	private long customerEmailID;

	private String paymentMode;

	private List<ReceivedOrderItem> products;

	

	public long getCustomerEmailID() {
		return customerEmailID;
	}

	public void setCustomerEmailID(long customerEmailID) {
		this.customerEmailID = customerEmailID;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public List<ReceivedOrderItem> getProducts() {
		return products;
	}

	public void setProducts(List<ReceivedOrderItem> products) {
		this.products = products;
	}
}

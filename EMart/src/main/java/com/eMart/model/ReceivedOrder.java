package com.eMart.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by maharshigor on 09/07/16.
 */
public class ReceivedOrder {

	@JsonProperty(value = "id")
	private Long orderID;

	@JsonProperty(value = "customer_id")
	private Long customerID;

	@JsonProperty(value = "user_name")
	private String customerEmailID;

	@JsonProperty(value = "mode")
	private String paymentMode;

	@JsonProperty(value = "status")
	private String status;

	private List<ReceivedOrderItem> products;

	public String getCustomerEmailID() {
		return customerEmailID;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public void setCustomerEmailID(String customerEmailID) {
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

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
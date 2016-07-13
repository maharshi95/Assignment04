package com.eMart.model;

import java.io.Serializable;

/**
 * Created by maharshigor on 09/07/16.
 */
public class OrderItemID implements Serializable {
	long orderID;
	long productID;

	public OrderItemID() {

	}

	public OrderItemID(long orderID, long productID) {
		this.orderID = orderID;
		this.productID = productID;
	}
}

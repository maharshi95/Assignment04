package com.eMart.model;

/**
 * Created by maharshigor on 09/07/16.
 */
public class ReceivedOrderItem {

	private long productID;

	private long quantity;

	private double sell_price;

	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getSell_price() {
		return sell_price;
	}

	public void setSell_price(double sell_price) {
		this.sell_price = sell_price;
	}
}

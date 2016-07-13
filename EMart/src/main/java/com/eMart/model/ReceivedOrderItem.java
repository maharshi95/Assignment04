package com.eMart.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by maharshigor on 09/07/16.
 */
public class ReceivedOrderItem {

	@JsonProperty(value = "product_id")
	private Long productID;

	@JsonProperty(value = "qty")
	private Long quantity;

	@JsonProperty(value = "sell_price")
	private double sell_price;

	public Long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getSellPrice() {
		return sell_price;
	}

	public void setSellPrice(Double sell_price) {
		this.sell_price = sell_price;
	}
}

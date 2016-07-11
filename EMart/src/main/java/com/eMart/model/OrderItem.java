package com.eMart.model;

import javax.persistence.*;

/**
 * Created by maharshigor on 08/07/16.
 */

@Entity
@IdClass(OrderItemID.class)
@Table(name = "OrderDetails")
public class OrderItem {

	@Id
	@Column(name = "order_id")
	private long orderID;

	@Id
	@Column(name = "product_id")
	private long productID;

	@Column(name = "quantity")
	private long quantity;

	@Column(name = "sell_price")
	private double sellPrice;

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

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

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	@Override
	public String toString() {
		return "OrderItem{" +
				"orderID=" + orderID +
				", productID=" + productID +
				", quantity=" + quantity +
				", sellPrice=" + sellPrice +
				'}';
	}
}
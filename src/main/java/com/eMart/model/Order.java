package com.eMart.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by maharshigor on 08/07/16.
 */
@Entity
@Table(name = "Orders")
public class Order {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderID;

	@Column(name = "customer_id")
	private long customerID;

	@Column(name = "time_created")
	private Date dateCreated;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "payment_status")
	private String paymentStatus;

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderID=" + orderID +
				", customerID=" + customerID +
				", dateCreated=" + dateCreated +
				", orderStatus='" + orderStatus + '\'' +
				", paymentMode='" + paymentMode + '\'' +
				", paymentStatus='" + paymentStatus + '\'' +
				'}';
	}
}

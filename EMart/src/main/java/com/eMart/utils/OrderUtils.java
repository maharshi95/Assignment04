package com.eMart.utils;

import com.eMart.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maharshigor on 12/07/16.
 */
public class OrderUtils {
	public static OrderItem getOrderItemFromRecievedOrderItem(Long orderID, ReceivedOrderItem item) {
		OrderItem orderItem = new OrderItem ();
		orderItem.setOrderID (orderID);
		orderItem.setQuantity (item.getQuantity ());
		orderItem.setProductID (item.getProductID ());
		orderItem.setSellPrice (item.getSellPrice ());
		return orderItem;
	}

	public static List<OrderItem> getorderItemsfromRecievedOrder(Long orderID, ReceivedOrder receivedOrder) {
		List<OrderItem> orderItems = new ArrayList<OrderItem> ();
		for(ReceivedOrderItem item : receivedOrder.getProducts ()) {
			orderItems.add(getOrderItemFromRecievedOrderItem (orderID,item));
		}
		return orderItems;
	}

	public static Order getOrderFromRecievedOrder(Long orderID, ReceivedOrder receivedOrder) {
		Order order = new Order ();
		order.setDateCreated (new Date ());
		order.setPaymentStatus (PaymentStatus.getDefaultStatus ().name ());
		order.setPaymentMode (receivedOrder.getPaymentMode ());
		order.setCustomerID (receivedOrder.getCustomerID());
		order.setOrderStatus (OrderStatus.CHECKOUT.name());
		order.setOrderID (orderID);
		return order;
	}

	public static Order getEmptyOrder(Long customerID) {
		Order order = new Order ();
		order.setOrderStatus (OrderStatus.CREATED.name ());
		order.setCustomerID (customerID);
		order.setPaymentStatus (PaymentStatus.getDefaultStatus ().name ());
		order.setPaymentMode (PaymentMode.getDefaultPaymentMode ().name ());
		order.setDateCreated (new Date ());
		return order;
	}

	public static ReceivedOrder getUIOrderInstance(Order order, String userName) {
		ReceivedOrder uiOrder = new ReceivedOrder ();
		uiOrder.setOrderID (order.getOrderID ());
		uiOrder.setCustomerID (order.getCustomerID ());
		uiOrder.setCustomerEmailID (userName);
		uiOrder.setPaymentMode (order.getPaymentMode ().toLowerCase ());
		uiOrder.setStatus (order.getOrderStatus ().toLowerCase ());
		uiOrder.setProducts (null);
		return uiOrder;
	}
}

package com.eMart.services;

import com.eMart.model.*;
import com.eMart.repo.OrderItemRepository;
import com.eMart.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maharshigor on 09/07/16.
 */

@Component
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	public Order createOrder(ReceivedOrder receivedOrder) {
		Order order = new Order();
		order.setCustomerID(receivedOrder.getCustomerEmailID());
		order.setOrderStatus(OrderStatus.PLACED.name());
		order.setPaymentMode(PaymentMode.valueOf(receivedOrder.getPaymentMode()).name());
		order.setPaymentStatus(PaymentStatus.NOT_RECIEVED.name());
		order.setDateCreated(new Date());
		return order;
	}

	public List<OrderItem> createOrderItems(ReceivedOrder receivedOrder, Order order) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for(ReceivedOrderItem item : receivedOrder.getProducts()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderID(order.getOrderID());
			orderItem.setProductID(item.getProductID());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSellPrice(item.getSell_price());
		}
		return orderItems;
	}

	public List<OrderItem> getOrderItems(Long orderID) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		Iterator<OrderItem> it = orderRepository.getOrderItems(orderID).iterator();
		while (it.hasNext()) orderItems.add(it.next());
		return orderItems;
	}
}

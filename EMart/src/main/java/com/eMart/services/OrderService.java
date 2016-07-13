package com.eMart.services;

import com.eMart.controller.OrderController;
import com.eMart.exceptions.BadOrderCreationExeption;
import com.eMart.exceptions.IllegalOrderModificationException;
import com.eMart.model.*;
import com.eMart.repo.OrderItemRepository;
import com.eMart.repo.OrderRepository;
import com.eMart.utils.CustomerUtils;
import com.eMart.utils.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by maharshigor on 09/07/16.
 */

@Component
public class OrderService {

	private final static Logger log = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	CustomerService customerService;

	@Autowired
	ProductService productService;

	public Order getActiveOrderByOrderID(Long orderID) {
		Order order = orderRepository.findOne (orderID);
		if (order != null && order.isDeleted ( )) order = null;
		return order;
	}

	public Order getOrderByOrderID(Long orderID) {
		return orderRepository.findOne (orderID);
	}

	public List<OrderItem> getOrderItemsByOrderId(Long orderID) {
		Iterator<OrderItem> it = orderRepository.getOrderItems (orderID).iterator ();
		List<OrderItem> orderItems = new ArrayList<OrderItem> ();
		while(it.hasNext ()) {
			orderItems.add (it.next ());
		}
		return orderItems;
	}

	/**
	 * Responsible for creating a new empty order for a given username.
	 * If userName exists in the database, it will create an order for the same,
	 * else it will create a new user with given username and create order for it.
	 * If userName is null, then an Empty unassigned order is created in database to which a username can be added in future
	 * @param userName Username of the Customer
	 * @return always returns a created order
	 */
	public Order createNewEmptyOrder(String userName) {
		log.info("Attempting to creating a new empty order for user:" + userName);
		Order order = null;
		if(userName == null) {
			log.info ("No user_name provided, Order will be created without user_name");
			order = OrderUtils.getEmptyOrder(null);
		} else {
			Customer customer = customerService.getCustomerByUserName (userName);
			if (customer == null) {
				log.info ("No Customer found with user_name : " +userName);
				log.info ("Creating a new customer with user_name: " + userName);
				customer = customerService.createNewCustomer (CustomerUtils.createNewCustomer (userName));
			}
			else {
				log.info ("Customer found with user_name:" + userName);
			}
			log.info ("Creating the order for user " + userName);
			order = OrderUtils.getEmptyOrder (customer.getCustomerID ());
		}
		order = orderRepository.save (order);
		log.info ("Order Creation SUCCESS!!");
		return order;
	}

	public boolean[] addItemsToOrder(Long orderID, List<ReceivedOrderItem> items) throws IllegalOrderModificationException {
		Order order = getActiveOrderByOrderID (orderID);
		boolean[] success = new boolean[items.size ( )];
		if(!order.isPlaced ()) {
			for (int i = 0; i < items.size ( ); i++) {
				try {
					OrderItem orderItem = OrderUtils.getOrderItemFromRecievedOrderItem (orderID, items.get (i));
					orderItemRepository.save (orderItem);
					success[i] = true;
				} catch (RuntimeException e) {
					success[i] = false;
				}
			}
		} else {
			throw new IllegalOrderModificationException ();
		}
		return success;
	}

	/**
	 * Responsible for adding a product, identified by productID, to a given order
	 * @param order The order into which the product need to be added. It can be assumed that order passed here will not be null
	 * @param item ReceivedOrderItem object item which need to be added to the order. It has attributes <B>productID</B> and <B>quantity</B>
	 *             The transaction will fail either if given order is already placed or the productID doesnt exist in database.
	 * @return true
	 * @throws IllegalOrderModificationException
	 */
	public boolean addItemToOrder(Order order, ReceivedOrderItem item) throws IllegalOrderModificationException {
		boolean success = false;
		if(!order.isPlaced ()) {
			try {
				OrderItem orderItem = OrderUtils.getOrderItemFromRecievedOrderItem (order.getOrderID (), item);
				OrderItem existingOrderItem = orderItemRepository.findOne (new OrderItemID (order.getOrderID (),orderItem.getProductID ()));
				if(existingOrderItem != null) {
					existingOrderItem.setQuantity (existingOrderItem.getQuantity () + orderItem.getQuantity ());
					if(existingOrderItem.getQuantity () < 0) existingOrderItem.setQuantity (0);
					orderItem = existingOrderItem;
				}
				orderItemRepository.save (orderItem);
				success = true;
			} catch (RuntimeException e) {
				success = false;
			}
		} else  {
			throw new IllegalOrderModificationException ();
		}
		return success;
	}

	/**
	 * Responsible for validating the consistency of the ordered quantity of products with the quantity of those products available in stock
	 * @param order Order object which needs to be verified
	 * @return true if all the OrderItems are consistent wrt the database
	 */
	public boolean isOrderValid(Order order) {
		boolean valid = true;
		List<OrderItem> orderItems = getOrderItemsByOrderId (order.getOrderID ());
		for(int i=0; i<orderItems.size () && valid; i++) {
			OrderItem item = orderItems.get(i);
			Product product = productService.getProductByProductID (item.getProductID ());
			valid = valid && item.getQuantity () <= product.getQuantity ();
		}
		return true;
	}

	public Order placeOrder(Long orderID, Map<String,String> patch) throws BadOrderCreationExeption {
		boolean success = false;
		Order order = getActiveOrderByOrderID(orderID);

		log.info ("Finding the order with order_id " + orderID);
		if(order != null && !order.isPlaced ()) {

			String status = patch.get("status");
			String userName = patch.get ("user_name");
			String address = patch.get ("address");
			log.info ("Order found.");
			log.info ("Request patch: " + patch);
			boolean statusCheck = status != null && status.equals ("checkout");
			if(statusCheck || true) {
				if(userName == null) {
					log.info ("user_name for order not found in patch");

					if(order.getCustomerID () != null) {
						log.info("Order " + orderID + "has a linked customer with id " + order.getCustomerID ());
						log.info ("Checking for consistancy of Order items with the inventory");

						if(isOrderValid (order)) {
							log.info ("Order is consistent");
							log.info ("Placing order " + orderID);

							order.setOrderStatus (OrderStatus.CHECKOUT.name ( ).toLowerCase ());
							order = orderRepository.save (order);
							success = true;
							log.info ("Order " + orderID +" placed");
						} else {
							log.info("Order items for order " + orderID + " are inconsistent with the inventory");
							log.info ("Failed to place Order " + orderID);
						}
					} else {
						log.info ("Order doesn't have any linked Customer to it");
						log.info ("Failed to place Order " + orderID);
					}
				}
				else {
					log.info ("Order has been requested for user_name " + userName);
					Customer customer = customerService.getCustomerByUserName (userName);
					if(customer == null) {
						log.info ("Customer with user_name " + userName + " doesnt exist");
						customer = customerService.createNewCustomer (userName);
						log.info ("Creating a new customer with user_name " + userName);
					}
					log.info ("CustomerID of " + userName + " : " + customer.getCustomerID ());
					if(order.getCustomerID () == null || order.getCustomerID ().equals (customer.getCustomerID ())) {
						if(order.getCustomerID () == null) {
							log.info ("patching customerID " + customer.getCustomerID () + " to the order");
						} else {
							log.info("customerID " + customer.getCustomerID () + " is already patched to the order");
						}
						log.info ("Checking for consistancy of Order items with the inventory");
						if(isOrderValid(order)) {
							log.info ("Order is consistent");
							log.info ("Placing order " + orderID);
							order.setCustomerID (customer.getCustomerID ( ));
							order.setOrderStatus (OrderStatus.CHECKOUT.name ( ));
							order = orderRepository.save (order);
							success = true;
							log.info ("Order " + orderID +" placed");
						} else {
							log.info("Order items for order " + orderID + " are inconsistent with the inventory");
							log.info ("Failed to place Order " + orderID);
						}
					} else {
						log.info ("CustomerID conflict for order " + orderID);
						log.info ("Requested patch CustomerID " + customer.getCustomerID () + " != existing customerID " + order.getCustomerID ());
						log.info ("Failed to place Order " + orderID);
					}
				}
			} else {
				log.info ("Invalid status for the order " + orderID + ": " + status);
			}
			if(success == false){
				throw new BadOrderCreationExeption ();
			}
		}
		if(success == false){
			if(order == null) {
				log.info ("Order not found for order_id: " + orderID);
			} else {
				log.info ("Attempt to place an already placed order");
				throw new BadOrderCreationExeption ();
			}
		}
		return order;
	}

	public boolean deleteOrder(Long orderID) {
		boolean success = false;
		Order order = getActiveOrderByOrderID (orderID);
		if(order != null) {
			order.setDeleted (true);
			orderRepository.save (order);
			success = true;
		}
		return success;
	}

	public Order createOrder(ReceivedOrder receivedOrder) {
		Order order = OrderUtils.getOrderFromRecievedOrder (0L,receivedOrder);
		order = orderRepository.save(order);
		try {
			addItemsToOrder(order.getOrderID (),receivedOrder.getProducts ());
		} catch (IllegalOrderModificationException e) {
			e.printStackTrace ();
		}
		return order;
	}

	public List<OrderItem> createOrderItems(ReceivedOrder receivedOrder, Order order) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for(ReceivedOrderItem item : receivedOrder.getProducts()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderID(order.getOrderID());
			orderItem.setProductID(item.getProductID());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSellPrice(item.getSellPrice ());
		}
		return orderItems;
	}

	public List<OrderItem> getOrderItems(Long orderID) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		Iterator<OrderItem> it = orderRepository.getOrderItems(orderID).iterator();
		if(it == null) orderItems = null;
		else
			while (it.hasNext()) orderItems.add(it.next());
		return orderItems;
	}
}

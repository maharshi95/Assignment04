package com.eMart.controller;

import com.eMart.exceptions.BadOrderCreationExeption;
import com.eMart.exceptions.IllegalOrderModificationException;
import com.eMart.model.*;
import com.eMart.repo.OrderRepository;
import com.eMart.services.CustomerService;
import com.eMart.services.OrderService;
import com.eMart.services.ProductService;
import com.eMart.utils.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by maharshigor on 08/07/16.
 */

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final static Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderService orderService;

	@Autowired
	CustomerService customerService;

	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Order> get(@PathVariable(value = "id") Long id) {
		log.info("order_id: " + id);
		Order order = orderService.getOrderByOrderID (id);
		HttpStatus status = HttpStatus.OK;
		if(order == null) {
			status = HttpStatus.NOT_FOUND;
		}
		else {
			log.info(order.getDateCreated().toString());
		}
		return new ResponseEntity<Order>(order, status);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/items/{orderID}")
	public ResponseEntity<?> getOrderItems(@PathVariable(value = "orderID") Long orderID) {
		List<OrderItem> orderItems = orderService.getOrderItems (orderID);
		HttpStatus status = HttpStatus.OK;
		if(orderItems == null) {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity (orderItems,status);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createNewOrder(@RequestBody Map<String,String> requestBody) {
		String userName = requestBody.get ("user_name");
		log.info ("Requested user_name: " + userName + " recieved");
		log.info("Calling orderService to create new order for User:" + userName);
		Order order = orderService.createNewEmptyOrder (userName);
		return new ResponseEntity(order,HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{id}/orderLineItem")
	public ResponseEntity<?> addItemsToOrder(@RequestBody ReceivedOrderItem orderItem, @PathVariable(value = "id") Long orderID) {
		Order order = orderService.getActiveOrderByOrderID (orderID);
		if(order == null) {
			return ResponseEntity.notFound().build ();
		}
		if(orderItem == null || orderItem.getProductID () == null || orderItem.getQuantity () == null) {
			return ResponseEntity.badRequest ().build ();
		}
		Product product = productService.getProductByProductID (orderItem.getProductID());
		if(product == null) {
			return ResponseEntity.notFound ().build ();
		}
		HttpStatus status = null;
		try {
			orderItem.setSellPrice (product.getSellPrice ());
			orderService.addItemToOrder (order, orderItem);
			status = HttpStatus.CREATED;
		} catch (IllegalOrderModificationException e) {
			log.error (e.getLocalizedMessage ( ));
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Object> (null,status);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
	public ResponseEntity<?> placeOrder(@RequestBody Map<String,String> patch, @PathVariable(value = "id") Long orderID) {
		Object responseBody = null;
		HttpStatus status = HttpStatus.CREATED;
		try {
			log.info ("Trying to place order " + orderID);
			Order order = orderService.placeOrder (orderID,patch);
			if(order == null) {
				status = HttpStatus.NOT_FOUND;
			} else {
				Customer c = customerService.getCustomerByID (order.getCustomerID ());
				responseBody = OrderUtils.getUIOrderInstance (order,c.getFirstName ());
				status = HttpStatus.CREATED;
			}

		} catch (BadOrderCreationExeption e) {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity(responseBody,status);
	}

	@RequestMapping(method = RequestMethod.DELETE,value = "{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable(value = "id") Long orderID) {
		boolean success = orderService.deleteOrder (orderID);
		HttpStatus status = success ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
		return new ResponseEntity(null,status);
	}

//	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map> createOrder(@RequestBody ReceivedOrder receivedOrder) {
		Map<String,Object> response = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		response.put("status","FAILURE");
		response.put("errorCode","BAD_REQUEST");
		response.put("reason","Bad Order Format");
		if(receivedOrder != null) {
			try {
				Order order = orderService.createOrder(receivedOrder);
				order = orderRepository.save(order);
				List<OrderItem> orderItems = orderService.createOrderItems(receivedOrder,order);
				response.put("status","SUCCESS");
				response.put("id",order.getOrderID());
				response.remove("errorCode");
				response.remove("reason");
				status = HttpStatus.ACCEPTED;
			} catch(RuntimeException e) {
				status = HttpStatus.NOT_ACCEPTABLE;
				response.put("errorCode","NOT_ACCECTABLE");
				response.put("reason","Violates Constraints in the database: " + e.getLocalizedMessage());
			}
		}
		return new ResponseEntity<Map>(response, status);
	}
}

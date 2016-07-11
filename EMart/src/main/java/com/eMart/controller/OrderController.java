package com.eMart.controller;

import com.eMart.model.Order;
import com.eMart.model.OrderItem;
import com.eMart.model.ReceivedOrder;
import com.eMart.repo.OrderRepository;
import com.eMart.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maharshigor on 08/07/16.
 */

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final static Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderService orderService;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Order> get(@PathVariable(value = "id") Long id) {
		log.debug("order_id: " + id);
		Order o = orderRepository.findOne(id);
		log.debug(o.getDateCreated().toString());
		return new ResponseEntity<Order>(o, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
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

package com.eMart.controller;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.eMart.model.Customer;
import com.eMart.model.OrderItem;
import com.eMart.services.SQSUtils;
import com.eMart.services.CustomerService;
import com.eMart.services.OrderService;
import com.eMart.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by maharshigor on 09/07/16.
 */

@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/customer/{email}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable(value = "email") String email) {
		Customer c = customerService.getCustomerByEmailID(email);
		log.debug(c.toString());
		return c;
	}

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public Customer getCustomer(@RequestParam(value = "id") Long id) {
		Customer c = customerService.getCustomerByID(id);
		log.debug(c.toString());
		return c;
	}

	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	public List<OrderItem> getOrderItems(@PathVariable(value = "id")Long id) {
		return orderService.getOrderItems(id);
	}


	@RequestMapping(value = "/sqs/create/{id}")
	public void qtest(@PathVariable(value = "id") Long id) {
		log.info ("Creating SQS Service instance");
		SQSUtils SQSUtils = new SQSUtils ();
		log.info ("Sending Message");
		SQSUtils.sendMessage ("This a test Message #"+id);
		log.info ("Message sent");
	}

	@RequestMapping(value = "/sqs/create/{name}")
	public void sqsTest(@PathVariable(value = "name")String queueName) {
		log.info ("Creating SQS");
		AmazonSQS sqs = SQSUtils.getClient ();
		log.info ("Creating Queue");
		CreateQueueResult rs = sqs.createQueue(queueName);
		log.info ("Queue creation result: " + rs.toString () + "");
	}
}

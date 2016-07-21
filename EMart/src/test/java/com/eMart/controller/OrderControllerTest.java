package com.eMart.controller;

import com.eMart.model.Order;
import com.eMart.model.Product;
import com.eMart.repo.OrderRepository;
import com.eMart.repo.ProductRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by maharshigor on 18/07/16.
 */

public class OrderControllerTest {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	private static final String baseURL = "http://localhost:8080/api";
	private static final String orderURL =  baseURL + "/products";
	private static final String invalidCustomerName = "invalidName";

	private static final int invalidId = -1;

	private Order order1;
	private Order order2;
	private Order order3;
	private Product product1;
	
	String uname1 = "testuser1";
	String uname2 = "testuser2";
	String uname3 = "testuser3";

	@Before
	public void setUp() throws Exception {
		order1 = new Order();
		order1.setOrderStatus ("CREATED");
		order2 = new Order();
		order2.setOrderStatus ("CREATED");
		order3 = new Order();
		order3.setOrderStatus ("CREATED");
		orderRepository.save(Arrays.asList(order1, order2, order3));

		product1 = new Product();
		product1.isDeleted ();
		product1.setBuyPrice(100.0);
		product1.setSellPrice(120.0);
		product1.setQuantity (100);
		product1.setProductCode ("abcd");
		product1.setName ("test_product");
		productRepository.save(product1);
	}



	@Test
	public void emptyUserTest() throws Exception {
		Response response = RestAssured.given ()
				.contentType (ContentType.JSON)
				.body (new HashMap<String,String> ())
				.when ()
				.post (orderURL)
				.then ()
				.statusCode (HttpStatus.CREATED.value ())
				.body ("username", Matchers.isEmptyOrNullString ())
				.extract ().response ();

		Long orderID = Long.valueOf ((Integer)response.path ("id"));

		String username = response.path ("username");
		Assert.assertNotNull (orderID);
		Assert.assertNull(username);

		response = RestAssured.given ()
				.when ()
				.get (orderURL + "/" + orderID)
				.then ()
				.statusCode (HttpStatus.CREATED.value ())
				.body ("username", Matchers.isEmptyOrNullString ())
				.extract ().response ();

	}
	@Test
	public void deleteOrder() {                       
		Long order2Id = order2.getOrderID ();
		Map<String, Object> input = new HashMap<>();
		Long product1Id = product1.getProductID ();
		input.put("product_id", product1Id);
		Long qty = product1.getQuantity ();
		input.put("qty", qty.intValue());
		RestAssured.when().
				delete("/api/orders/{pk}", order2Id).
				then().
				statusCode(HttpStatus.OK.value ());

		RestAssured.given().                               
				contentType(ContentType.JSON).
				body(input).
				when().
				patch("/api/orders/{pk}", order2Id).
				then().
				statusCode(HttpStatus.NOT_FOUND.value ());

		RestAssured.when().
				delete("/api/orders/{pk}", order2Id).      
				then().
				statusCode(HttpStatus.NOT_FOUND.value ());


		RestAssured.given().                              
				contentType(ContentType.JSON).
				body(input).
				when().
				post("/api/orders/{pk}/orderLineItem", order2Id).
				then().
				statusCode(HttpStatus.NOT_FOUND.value ());


		RestAssured.when().                                 
				get("/api/orders/{order_id}", order2Id).
				then().
				statusCode(HttpStatus.NOT_FOUND.value ());
	}

	@Test
	public void deleteInvalidOrder() {                       
		RestAssured.when().
				delete("/api/orders/{pk}", invalidId).
				then().
				statusCode(HttpStatus.NOT_FOUND.value ());
	}


	@Test
	public void getOrder() {                   
		Long orders1Id = order1.getOrderID ();
		RestAssured.when().
				get("/api/orders/{id}", orders1Id).
				then().
				statusCode(HttpStatus.OK.value ()).
				body("status", Matchers.is("Order Created"));

	}

	@Test
	public void getInvalidOrder() {                   
		RestAssured.when().
				get("/api/orders/{id}", invalidId).
				then().
				statusCode(HttpStatus.NOT_FOUND.value ());

	}


	@Test
	public void postOrder() {                 
		RestAssured.when().
				post("/api/orders").
				then().
				statusCode(HttpStatus.NOT_FOUND.value ());
	}

}
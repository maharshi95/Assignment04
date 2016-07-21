package com.eMart.controller;

import com.eMart.Application;
import com.eMart.model.Product;
import com.eMart.repo.ProductRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

/**
 * Created by maharshigor on 17/07/16.
 */
@RunWith (SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)

public class ProductControllerTest {

	private final String baseURL = "http://localhost:8080";
	private final String productURL = baseURL + "/api/products";

	public static final Logger LOGGER = LoggerFactory.getLogger (ProductControllerTest.class);

	@Autowired
	ProductRepository productRepository;

	Product p1,p2,p3;
	Long id1,id2,id3,id4;

	@Before
	public void setUp() throws Exception {
		p1 = new Product ();
		p1.setProductCode ("code1");
		p1.setDescription ("base description");
		p1.setQuantity (10);
		p1.setBuyPrice (100);
		p1.setSellPrice (200);

		p2 = new Product ();
		p2.setProductCode ("code2");
		p2.setDescription ("base description");
		p2.setQuantity (10);
		p2.setBuyPrice (100);
		p2.setSellPrice (200);

		p3 = new Product ();
		p3.setProductCode ("code3");
		p3.setDescription ("base description");
		p3.setQuantity (20);
		p3.setBuyPrice (150);
		p3.setSellPrice (250);

		p3 = productRepository.save (p3);
		p2 = productRepository.save (p2);
		LOGGER.info (p3.toString ());
	}

	@After
	public void tearDown() throws Exception {
		RestAssured.given ()
				.when ()
				.delete (productURL+"/"+p2.getProductID ())
				.then ()
				.statusCode (HttpStatus.NO_CONTENT.value ())
				.extract ().response ();

		RestAssured.given ()
				.when ()
				.delete (productURL+"/"+p2.getProductID ())
				.then ()
				.statusCode (HttpStatus.NO_CONTENT.value ())
				.extract ().response ();
	}

	@Test
	public void getAll() throws Exception {
		Response response = RestAssured.given ()
				.when ()
				.get (productURL)
				.then ()
				.statusCode (HttpStatus.OK.value ())
				.extract ()
				.response ();
	}

	@Test
	public void get() throws Exception {
		Response response = RestAssured.given ()
				.when ()
				.get (productURL+"/"+p3.getProductID ())
				.then ()
				.statusCode (HttpStatus.OK.value ())
				.body ("code", Matchers.is (p3.getProductCode ()))
				.body ("description", Matchers.is (p3.getDescription ()))
				.extract ().response ();
		Assert.assertNotNull (response.path ("id"));

		response = RestAssured.given ()
				.when ()
				.get (productURL+"/"+p2.getProductID ())
				.then ()
				.statusCode (HttpStatus.OK.value ())
				.body ("code", Matchers.is (p2.getProductCode ()))
				.body ("description", Matchers.is (p2.getDescription ()))
				.extract ().response ();
		Assert.assertNotNull (response.path ("id"));

		Long randomID = 9231L;
		response = RestAssured.given ()
				.when ()
				.get (productURL+"/"+randomID)
				.then ()
				.statusCode (HttpStatus.NOT_FOUND.value ())
				.extract ().response ();
	}

	@Test
	public void allFunctionalityTest() {
		Response response = RestAssured.given ()
				.contentType (ContentType.JSON)
				.body (p1)
				.when ()
				.post (productURL)
				.then ()
				.statusCode (HttpStatus.CREATED.value ())
				.body ("code", Matchers.is (p1.getProductCode ()))
				.body ("description", Matchers.is (p1.getDescription ()))
				.extract ().response ();
		p1.setProductID (Long.valueOf ((Integer)response.path ("id")));
		Assert.assertNotNull (p1.getProductID ());

		response = RestAssured.given ()
				.contentType (ContentType.JSON)
				.body ("")
				.when ()
				.post (productURL)
				.then ()
				.statusCode (HttpStatus.BAD_REQUEST.value ())
				.extract ().response ();

		LOGGER.info (productURL+"/"+p1.getProductID ());
		response = RestAssured.given ()
				.when ()
				.get (productURL+"/"+p1.getProductID ())
				.then ()
				.statusCode (HttpStatus.OK.value ())
				.body ("code", Matchers.is (p1.getProductCode ()))
				.body ("description", Matchers.is (p1.getDescription ()))
				.extract ().response ();
		Assert.assertNotNull (response.path ("id"));

		HashMap<String,Object> patch = new HashMap<String,Object> ();
		patch.put ("code","newCode");
		RestAssured.given ()
				.contentType (ContentType.JSON)
				.body (patch)
				.when ()
				.patch (productURL+"/"+p1.getProductID ())
				.then ()
				.statusCode (HttpStatus.OK.value ())
				.body ("code", Matchers.is ("newCode"))
				.body ("description", Matchers.is (p1.getDescription ()))
				.extract ().response ();

		HashMap<String,Object> put = new HashMap<String,Object> ();
		put.put ("code","basecode");
		put.put ("description","udes");
		RestAssured.given ()
				.contentType (ContentType.JSON)
				.body (put)
				.when ()
				.put (productURL+"/"+p1.getProductID ())
				.then ()
				.statusCode (HttpStatus.OK.value ())
				.body ("code", Matchers.is ("basecode"))
				.body ("description", Matchers.is ("udes"))
				.extract ().response ();

		RestAssured.given ()
				.when ()
				.delete (productURL+"/"+p1.getProductID ())
				.then ()
				.statusCode (HttpStatus.NO_CONTENT.value ())
				.extract ().response ();

		RestAssured.given ()
				.when ()
				.delete (productURL+"/"+p1.getProductID ())
				.then ()
				.statusCode (HttpStatus.NOT_FOUND.value ())
				.extract ().response ();

		put.put ("code","basecode");
		put.put ("description","udes");
		RestAssured.given ()
				.contentType (ContentType.JSON)
				.body (put)
				.when ()
				.put (productURL+"/"+p1.getProductID ())
				.then ()
				.statusCode (HttpStatus.NOT_FOUND.value ())
				.extract ().response ();

		patch.put ("code","newCode");
		RestAssured.given ()
				.contentType (ContentType.JSON)
				.body (patch)
				.when ()
				.patch (productURL+"/"+p1.getProductID ())
				.then ()
				.statusCode (HttpStatus.NOT_FOUND.value ())
				.extract ().response ();
	}

}
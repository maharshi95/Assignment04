package com.eMart.controller;

import com.eMart.Application;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by maharshigor on 17/07/16.
 */
@RunWith (SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)
public class HeathControllerTest {

	private final String baseURL = "http://localhost:8080";
	private final String healthURL = baseURL + "/api/health";

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void check() throws Exception {
		Response response = RestAssured.given ()
				.when()
				.get(healthURL)
				.then ()
				.statusCode (HttpStatus.OK.value ())
				.extract ().response ();
	}

}
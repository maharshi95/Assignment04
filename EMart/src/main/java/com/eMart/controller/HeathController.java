package com.eMart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by maharshigor on 13/07/16.
 */
@RestController
@RequestMapping(value = "/api")
public class HeathController {

	@RequestMapping(value = "/health")
	public ResponseEntity<Object> check() {
		return ResponseEntity.ok (null);
	}
}

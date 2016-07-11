package com.eMart.controller;

import com.eMart.exceptions.BadProductFormatException;
import com.eMart.exceptions.ProductNotFoundException;
import com.eMart.model.Product;
import com.eMart.repo.ProductRepository;
import com.eMart.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by maharshigor on 08/07/16.
 */

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final static String NOT_FOUND = "Not Found.";

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductService productService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List> getAll() {
		return new ResponseEntity<List>(productService.getAllProducts(),HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET,value = "/{id}")
	public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
		Object responseBody = productService.getProductByProductID(id);
		HttpStatus status = HttpStatus.OK;
		if(responseBody == null) {
			responseBody = new HashMap<String,Object>();
			((HashMap<String,Object>) responseBody).put("detail",NOT_FOUND);
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity(responseBody,status);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Product product) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Object reponseBody = product;
		HttpStatus status = null;
		if(product == null) {
			responseMap.put("detail","No Product");
			reponseBody = responseMap;
			status = HttpStatus.NOT_ACCEPTABLE;
		}
		else {
			try {
				Long productID = productService.insertProduct(product);
				product.setProductID(productID);
				status = HttpStatus.CREATED;
			} catch(BadProductFormatException e) {
				responseMap.put("detail","Bad Product Format");
				reponseBody = responseMap;
				status = HttpStatus.NOT_ACCEPTABLE;
			}
		}
		return new ResponseEntity(reponseBody, status);
	}

	@RequestMapping(method = RequestMethod.PUT,value = "/{id}")
	public ResponseEntity<?> updateById(@PathVariable(value = "id") Long id, @RequestBody Product product) {
		HttpStatus status = HttpStatus.OK;
		Object responseBody = productService.updateProduct(id,product);
		if(responseBody == null) {
			responseBody = new HashMap<String,Object>();
			((HashMap<String,Object>) responseBody).put("detail",NOT_FOUND);
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<Object>(responseBody, status);
	}

	@RequestMapping(method = RequestMethod.PATCH,value = "/{id}")
	public ResponseEntity<?> patchById(@PathVariable(value = "id") Long id, @RequestBody Map<String,String> patch) {
		HttpStatus status = null;
		Object responseBody = null;
		Product product = productService.getProductByProductID(id);
		if(product == null) {
			responseBody = new HashMap<String,Object>();
			((HashMap<String,Object>) responseBody).put("detail",NOT_FOUND);
			status = HttpStatus.NOT_FOUND;
		} else  {
			String productCode = patch.get("code");
			String description = patch.get("description");
			if(productCode != null) product.setProductCode(productCode);
			if(description != null) product.setDescription(description);
			responseBody = productService.updateProduct(id,product);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Object>(responseBody, status);
	}

	@RequestMapping(method = RequestMethod.DELETE,value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("detail",NOT_FOUND);
		HttpStatus status = HttpStatus.NOT_FOUND;
		boolean success = productService.deleteProduct(id);
		if(success) {
			status = HttpStatus.NO_CONTENT;
			response = null;
		}
		return new ResponseEntity<Object>(response,status);
	}

}
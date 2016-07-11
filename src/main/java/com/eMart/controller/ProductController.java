package com.eMart.controller;

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
@RequestMapping("/products")
public class ProductController {

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
		Object product = productService.getProductByProductID(id);
		HttpStatus status = HttpStatus.OK;
		if(product == null) {
			product = new HashMap<String,Object>();
			((HashMap<String,Object>) product).put("detail","Not found.");
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity(product,HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Product product) {
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		Object obj = null;
		if(product != null) {
			try {
				obj = productRepository.save(product);
				status = HttpStatus.CREATED;
			} catch(RuntimeException e) {
				status = HttpStatus.NOT_ACCEPTABLE;
				Map<String,Object> response = new HashMap<String,Object>();
				response.put("status","FAILURE");
				obj = response;
			}
		}
		return new ResponseEntity(obj, status);
	}

//	@RequestMapping(method = RequestMethod.PUT,value = "/{id}")
//	public ResponseEntity<Map> updateById(@PathVariable(value = "id") Long id, @RequestBody Product updatedProduct) {
//		Map<String, Object> response = new HashMap<String, Object>();
//		HttpStatus status = HttpStatus.NOT_FOUND;
//		response.put("detail","Not found.");
//		Product fetchedProduct = productRepository.findOne(id);
//		if(fetchedProduct != null) {
//			status = HttpStatus.BAD_REQUEST;
//			response.put("status", "FAILURE");
//			response.put("errorCode", "BAD_REQUEST");
//			response.put("reason", "Bad Product Format");
//			if (updatedProduct != null) {
//				try {
//					updatedProduct.setProductID(fetchedProduct.getProductID());
//					productRepository.save(updatedProduct);
//					response.put("status", "SUCCESS");
//					response.remove("errorCode");
//					response.remove("reason");
//					status = HttpStatus.ACCEPTED;
//				} catch (RuntimeException e) {
//					status = HttpStatus.NOT_ACCEPTABLE;
//					response.put("errorCode", "NOT_ACCECTABLE");
//					response.put("reason", "Violates Constraints in the database: " + e.getLocalizedMessage());
//				}
//			}
//		}
//		return new ResponseEntity<Map>(response, status);
//	}

	@RequestMapping(method = RequestMethod.DELETE,value = "/{id}")
	public ResponseEntity<Map> delete(@PathVariable(value = "id") Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		Product fetchedProduct = productRepository.findOne(id);

		HttpStatus status = HttpStatus.NOT_FOUND;
		response.put("status","FAILURE");
		response.put("errorCode","NOT_FOUND");
		response.put("reason","Product id not found");

		if(fetchedProduct != null) {
			try {
				fetchedProduct.setDeleted(true);
				productRepository.save(fetchedProduct);
				response.put("status", "SUCCESS");
				response.remove("errorCode");
				response.remove("reason");
				status = HttpStatus.ACCEPTED;
			} catch (RuntimeException e) {
				status = HttpStatus.REQUEST_TIMEOUT;
				response.put("errorCode", "REQUEST_TIMEOUT");
				response.put("reason", "Request Timed out");
			}
		}
		return new ResponseEntity<Map>(response, status);
	}

}
package com.eMart.services;

import com.eMart.exceptions.BadProductFormatException;
import com.eMart.exceptions.ProductNotFoundException;
import com.eMart.model.Product;
import com.eMart.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maharshigor on 09/07/16.
 */

@Component
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product getProductByCode(String code) {
		Product product = productRepository.findByCode(code);
		if(product.isDeleted()) product = null;
		return product;
	}

	public Product getProductByProductID(Long productID) {
		Product product = productRepository.findOne(productID);
		if(product.isDeleted()) product = null;
		return product;
	}

	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<Product>();
		Iterator<Product> it = productRepository.findAll().iterator();
		while (it.hasNext()) {
			Product product = it.next();
			if(!product.isDeleted())
				products.add(product);
		}
		return products;
	}

	public Long insertProduct(Product product) {
		Long productID = null;
		try {
			product = productRepository.save(product);
			productID = product.getProductID();
		} catch (RuntimeException e) {
			throw new BadProductFormatException();
		}
		return productID;
	}

	public boolean deleteProduct(Long productID) {
		Product product = getProductByProductID(productID);
		boolean success = false;
		if(product != null) {
			product.setDeleted(true);
			productRepository.save(product);
			success = true;
		}
		return success;
	}

	public Product updateProduct(Long productID, Product product) {
		Product oldProduct = getProductByProductID(productID);
		if(oldProduct != null) {
			oldProduct.setProductCode(product.getProductCode());
			if(product.getDescription().equals(""))
				oldProduct.setDescription(product.getDescription());
			productRepository.save(oldProduct);
		}
		return oldProduct;
	}



}

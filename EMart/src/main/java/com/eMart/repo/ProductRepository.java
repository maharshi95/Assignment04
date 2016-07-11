package com.eMart.repo;

/**
 * Created by maharshigor on 08/07/16.
 */

import java.util.List;

import com.eMart.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


public interface ProductRepository extends CrudRepository<Product,Long> {

	@Query("select p from Product p where p.productCode = ?1")
	public Product findByCode(String code);

}

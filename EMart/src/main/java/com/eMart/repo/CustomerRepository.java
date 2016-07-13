package com.eMart.repo;

import com.eMart.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by maharshigor on 09/07/16.
 */
public interface CustomerRepository extends CrudRepository<Customer,Long> {

	@Query("select c from Customer c where c.emailID = ?1")
	public Customer getByEmailID(String emailID);

	@Query("select c from Customer c where c.firstName = ?1")
	public Customer getByUserName(String userName);

}

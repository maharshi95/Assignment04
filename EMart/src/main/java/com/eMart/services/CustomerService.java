package com.eMart.services;

import com.eMart.model.Address;
import com.eMart.model.Customer;
import com.eMart.repo.AddressRepository;
import com.eMart.repo.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maharshigor on 09/07/16.
 */

@Component
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AddressRepository addressRepository;

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

	public Customer getCustomerByEmailID(String emailID) {
		Customer customer = customerRepository.getByEmailID(emailID);
		return customer;
	}

	public Customer getCustomerByID(Long id) {
		return customerRepository.findOne(id);
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		Iterator<Customer> it = customerRepository.findAll().iterator();
		while (it.hasNext()) customers.add(it.next());
		return customers;
	}

	public boolean emailIdExists(String emailID) {
		return (customerRepository.getByEmailID(emailID) != null);
	}

	public Address getCurrentAddressOfCustomer(Long customerID) {
		Customer customer = getCustomerByID(customerID);
		Address address = null;
		if(customer != null) {
			Long addressID = customer.getCurrentAddressID();
			address = addressRepository.findOne(addressID);
		}
		return address;
	}
}

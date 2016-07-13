package com.eMart.utils;

import com.eMart.model.Customer;

/**
 * Created by maharshigor on 13/07/16.
 */
public class CustomerUtils {
	public static Customer createNewCustomer(String userName) {
		Customer c = new Customer ();
		c.setFirstName (userName);
		c.setEmailID (genrateEmailID (userName));
		return c;
	}

	public static String genrateEmailID(String userName) {
		return userName + "@gmail.com";
	}
}

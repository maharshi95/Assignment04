package com.eMart.model;

import javax.persistence.*;

/**
 * Created by maharshigor on 09/07/16.
 */

@Entity
@Table(name = "Customer")
public class Customer {

	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long customerID;

	@Column(name = "email_id")
	private String emailID;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "contact")
	private String contact;

	@Column(name = "current_add_id")
	private long currentAddressID;

	public Customer() { }

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public long getCurrentAddressID() {
		return currentAddressID;
	}

	public void setCurrentAddressID(long currentAddressID) {
		this.currentAddressID = currentAddressID;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"customerID=" + customerID +
				", emailID='" + emailID + '\'' +
				", companyName='" + companyName + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", contact='" + contact + '\'' +
				", currentAddressID=" + currentAddressID +
				'}';
	}
}

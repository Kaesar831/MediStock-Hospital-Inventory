package com.medistock.model;

public class Supplier {
	// Attributes
	private String supplierId;
	private String name;
	private String ContactPerson;
	private String phoneNumber;
	
	// Constructor
	public Supplier(String supplierId, String name, String contactPerson, String phoneNumber) {
		super();
		this.supplierId = supplierId;
		this.name = name;
		ContactPerson = contactPerson;
		this.phoneNumber = phoneNumber;
	}

	// Getters and Setters Methods
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return ContactPerson;
	}

	public void setContactPerson(String contactPerson) {
		ContactPerson = contactPerson;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	
	
	

}

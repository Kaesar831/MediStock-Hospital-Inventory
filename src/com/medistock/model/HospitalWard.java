package com.medistock.model;

public class HospitalWard {
	// Attributes
	private String wardId;
	private String name;
	private String department;
	private int capacity;
	
	// Constructor
	public HospitalWard(String wardId, String name, String department, int capacity) {
		super();
		this.wardId = wardId;
		this.name = name;
		this.department = department;
		this.capacity = capacity;
	}

	// Getters and Setters Methods
	public String getWardId() {
		return wardId;
	}

	public void setWardId(String wardId) {
		this.wardId = wardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	
	
	

}

package com.medistock.model;

import java.time.LocalDate;

public class InventoryItem {
	// Core Attributes
	private String itemId;
	private String name;
	private int quantity;
	private double unitCost; // Cost per item
	private LocalDate expirationDate;
	private Supplier supplier;
	private HospitalWard location;
	private String batchNumber;
	
	// Constructor
	public InventoryItem(String itemId, String name, int quantity, double unitCost, LocalDate expirationDate,
			Supplier supplier, HospitalWard location, String batchNumber) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.quantity = quantity;
		this.unitCost = unitCost;
		this.expirationDate = expirationDate;
		this.supplier = supplier;
		this.location = location;
		this.batchNumber = batchNumber;
	}
	
	// Getters and Setters Methods

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public HospitalWard getLocation() {
		return location;
	}

	public void setLocation(HospitalWard location) {
		this.location = location;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	
	
	
	
	
	

}

package com.medistock.model;

import java.time.LocalDate;

public class InventoryItem implements Comparable<InventoryItem> {
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

	@Override
	public String toString() {
		return "InventoryItem [itemId=" + itemId + ", name=" + name + ", quantity=" + quantity + ", unitCost="
				+ unitCost + ", expirationDate=" + expirationDate + ", supplier=" + supplier + ", location=" + location
				+ ", batchNumber=" + batchNumber + "]";
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
	
	@Override
	public int compareTo(InventoryItem other) {
		// This sorts items so the earliest expiration date come first
		if (this.expirationDate == null || other.expirationDate == null) return 0;
		return this.expirationDate.compareTo(other.expirationDate);
	}
	
	
	
	
	
	

}

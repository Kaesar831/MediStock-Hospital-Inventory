package com.medistock.model;

import java.time.LocalDate;

public class Medication extends InventoryItem {
	// Specific Attributes for Medication
	private String dosageForm; // e.g., Tablet, Capsule, Liquid
	private String ndcCode; // national drug code - Critical identifier
	private boolean isControlledSubstance;
	// This is where reimbursement logic will hook in later
	
	// Constructor
	public Medication(String itemId, String name, int quantity, double unitCost, LocalDate expirationDate,
			Supplier supplier, HospitalWard location, String batchNumber, String dosageForm, String ndcCode, boolean isControlledSubstance) {
		super(itemId, name, quantity, unitCost, expirationDate, supplier, location, batchNumber);
		// TODO Auto-generated constructor stub
		this.dosageForm = dosageForm;
		this.ndcCode = ndcCode;
		this.isControlledSubstance = isControlledSubstance;
	}
	
	// Getters and Setters Methods
	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getNdcCode() {
		return ndcCode;
	}

	public void setNdcCode(String ndcCode) {
		this.ndcCode = ndcCode;
	}

	public boolean isControlledSubstance() {
		return isControlledSubstance;
	}

	public void setControlledSubstance(boolean isControlledSubstance) {
		this.isControlledSubstance = isControlledSubstance;
	}
	
	
	
	
	
}

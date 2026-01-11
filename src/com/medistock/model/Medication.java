package com.medistock.model;

import java.time.LocalDate;

public class Medication extends InventoryItem {
	// Specific Attributes for Medication
	private String dosageForm; // e.g., Tablet, Capsule, Liquid
	private String ndcCode; // national drug code - Critical identifier
	private boolean isControlledSubstance;
	private boolean isGeneric;
	// This is where reimbursement logic will hook in later
	public static final double MEDICARE_PART_B_FACTOR = 1.06; // ASP + 6%
    public static final double DRUG_340B_DISCOUNT_FACTOR = 0.75; // Typical 25% discount
    public static final double BRAND_REIMBURSEMENT_FACTOR = 1.06;
    public static final double GENERIC_REIMBURSEMENT_FACTOR = 1.08;
	
	// Constructor
	public Medication(String itemId, String name, int quantity, double unitCost, LocalDate expirationDate,
			Supplier supplier, HospitalWard location, String batchNumber, String dosageForm, String ndcCode, 
			boolean isControlledSubstance, boolean isGeneric) {
		super(itemId, name, quantity, unitCost, expirationDate, supplier, location, batchNumber);
		// TODO Auto-generated constructor stub
		this.dosageForm = dosageForm;
		this.ndcCode = ndcCode;
		this.isControlledSubstance = isControlledSubstance;
		this.isGeneric = isGeneric;
	}
	
	// Getters and Setters Methods
	public String getDosageForm() {
		return dosageForm;
	}

	@Override
	public String toString() {
		return "Medication [dosageForm=" + dosageForm + ", ndcCode=" + ndcCode + ", isControlledSubstance="
				+ isControlledSubstance + "]";
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
	
	public boolean isGeneric() { 
		return isGeneric; 
	}

	public void setControlledSubstance(boolean isControlledSubstance) {
		this.isControlledSubstance = isControlledSubstance;
	}
	
	/**
	 * Calculates the estimated government reimbursement based on the 
	 * Medicare Part B formula: (ASP + 6%).
	 * Uses the professional constant for Medicare Part B (ASP + 6%).
	 * Implements core CMS reimbursement logic.
     * Differentiates between Branded and Generic drug types.
	 * @return total reimbursement amount
	 */
	public double calculateReimbursement() {
	    double baseValue = this.getUnitCost() * this.getQuantity();
	    if (this.isGeneric) {
            return baseValue * GENERIC_REIMBURSEMENT_FACTOR;
        } else {
            return baseValue * BRAND_REIMBURSEMENT_FACTOR;
        }
	   
	}
	
	/**
     * Added logic for 340B pricing
     * Calculates what the hospital pays after the 340B discount.
     */
    public double calculate340BCost() {
        return (this.getUnitCost() * this.getQuantity()) * DRUG_340B_DISCOUNT_FACTOR;
    }
	
	
	
	
	
}

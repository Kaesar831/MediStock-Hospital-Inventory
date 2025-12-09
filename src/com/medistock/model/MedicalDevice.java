package com.medistock.model;

import java.time.LocalDate;

public class MedicalDevice extends InventoryItem {
	// Specific Attributes for Medical Devices
	private String deviceModel;
	private String lotNumber;
	private boolean isSterile;
	private LocalDate lastCalibrationDate;
	
	// Constructor
	public MedicalDevice(String itemId, String name, int quantity, double unitCost, LocalDate expirationDate,
			Supplier supplier, HospitalWard location, String batchNumber, String deviceModel, String lotNumber, boolean isSterile, LocalDate lastCalibrationDate) {
		super(itemId, name, quantity, unitCost, expirationDate, supplier, location, batchNumber);
		// TODO Auto-generated constructor stub
		
		this.deviceModel = deviceModel;
		this.lotNumber = lotNumber;
		this.isSterile = isSterile;
		this.lastCalibrationDate = lastCalibrationDate;
	}
	
	// Getters and Setters Methods
	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public boolean isSterile() {
		return isSterile;
	}

	public void setSterile(boolean isSterile) {
		this.isSterile = isSterile;
	}

	public LocalDate getLastCalibrationDate() {
		return lastCalibrationDate;
	}

	public void setLastCalibrationDate(LocalDate lastCalibrationDate) {
		this.lastCalibrationDate = lastCalibrationDate;
	}
	
	
	
	
	

}

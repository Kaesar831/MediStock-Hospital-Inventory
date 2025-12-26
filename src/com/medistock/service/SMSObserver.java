package com.medistock.service;

public class SMSObserver implements AlertObserver {
	private String phoneNumber;

    public SMSObserver(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void onStockAlert(String message) {
        System.out.println("📱 [SMS SENT TO " + phoneNumber + "]: ALERT: " + message);
    }

}

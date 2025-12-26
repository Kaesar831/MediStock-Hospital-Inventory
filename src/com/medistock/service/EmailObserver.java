package com.medistock.service;

public class EmailObserver implements AlertObserver {
	private String email;

    public EmailObserver(String email) {
        this.email = email;
    }

    @Override
    public void onStockAlert(String message) {
        System.out.println("📧 [EMAIL SENT TO " + email + "]: " + message);
    }

}

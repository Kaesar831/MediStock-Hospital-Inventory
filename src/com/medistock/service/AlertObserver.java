package com.medistock.service;

public interface AlertObserver {
	// This is called when the Notifier finds something urgent
    void onStockAlert(String message);
}

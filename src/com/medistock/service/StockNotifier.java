package com.medistock.service;

import java.util.ArrayList;
import java.util.List;

public class StockNotifier {
	private List<AlertObserver> observers = new ArrayList<>();

    // Add a new observer (e.g., a new pharmacist's email)
    public void attach(AlertObserver observer) {
        observers.add(observer);
    }

    // Remove an observer
    public void detach(AlertObserver observer) {
        observers.remove(observer);
    }

    // The "Broadcast" method
    public void notifyObservers(String message) {
        for (AlertObserver observer : observers) {
            observer.onStockAlert(message);
        }
    }

}

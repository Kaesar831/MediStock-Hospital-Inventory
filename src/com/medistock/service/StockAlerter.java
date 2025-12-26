package com.medistock.service;

import com.medistock.model.InventoryItem;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class StockAlerter {
	// The PriorityQueue automatically keeps the soonest expiration date at the head
    private PriorityQueue<InventoryItem> expirationQueue;

    public StockAlerter() {
        this.expirationQueue = new PriorityQueue<>();
    }

    public void addItem(InventoryItem item) {
        expirationQueue.add(item);
    }

    /**
     * Returns items expiring within the next 'days' days.
     */
    public List<InventoryItem> getExpiringSoon(int days) {
        List<InventoryItem> expiringSoon = new ArrayList<>();
        java.time.LocalDate threshold = java.time.LocalDate.now().plusDays(days);

        // We use a temporary list because peek() only shows the top item
        // and we don't want to permanently remove items from the queue yet
        for (InventoryItem item : expirationQueue) {
            if (item.getExpirationDate().isBefore(threshold)) {
                expiringSoon.add(item);
            }
        }
        return expiringSoon;
    }

    public InventoryItem getMostUrgentItem() {
        return expirationQueue.peek(); // Returns the top item without removing it
    }
 
}

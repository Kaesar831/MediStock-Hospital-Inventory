package com.medistock.data;

import com.medistock.service.StockAlerter;
import com.medistock.service.StockNotifier;
import com.medistock.service.EmailObserver;
import com.medistock.service.SMSObserver;
import com.medistock.model.InventoryItem;
import com.medistock.data.CSVParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
	// Key: ItemID (String), Value: The InventoryItem object
    private Map<String, InventoryItem> inventory;
    private CSVParser parser;
    // Integrating StockAlerter into InventoryManager
    private StockAlerter alerter;
    // Add the Notifier field
    private StockNotifier notifier;
    // Define a threshold (e.g., alert if less than 10 units remain)
    private static final int LOW_STOCK_THRESHOLD = 10;

    public InventoryManager() {
        this.inventory = new HashMap<>();
        this.parser = new CSVParser();
        this.alerter = new StockAlerter();
        // Initialize Notifier and Attach Observers
        this.notifier = new StockNotifier();
        this.notifier.attach(new EmailObserver("admin@hospital.org"));
        this.notifier.attach(new SMSObserver("555-0199"));
    }

    /**
     * Calls the parser to load data and populates the internal Map.
     */
    public void loadInventoryFromCSV(String filePath) {
        try {
            List<InventoryItem> items = parser.readData(filePath);
            for (InventoryItem item : items) {
                // Store in map using ItemId as the unique key
                inventory.put(item.getItemId(), item);
                // Feed into the Alerter (for expiration tracking)
                alerter.addItem(item);
                // Trigger the low-stock check for every item loaded
                checkLowStock(item);
            }
            System.out.println("✅ Inventory Manager updated: " + inventory.size() + " items loaded.");
            System.out.println("✅ Inventory loaded and stock levels checked.");
        } catch (Exception e) {
            System.err.println("❌ Failed to load inventory: " + e.getMessage());
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    public int getInventoryCount() {
        return inventory.size();
    }

    public Map<String, InventoryItem> getAllItems() {
        return inventory;
    }
    
    public StockAlerter getAlerter() {
        return this.alerter;
    }
    
    /**
     * Checks if the item quantity is below the threshold and triggers notification.
     */
    private void checkLowStock(InventoryItem item) {
        if (item.getQuantity() < LOW_STOCK_THRESHOLD) {
            String alertMessage = "CRITICAL: " + item.getName() + 
                                  " (ID: " + item.getItemId() + 
                                  ") is low on stock! Current: " + item.getQuantity();
            
            notifier.notifyObservers(alertMessage);
        }
    }
    
    /**
     * Updates the quantity and automatically checks for low stock alerts.
     */
    public void updateItemQuantity(String itemId, int newQuantity) {
        InventoryItem item = inventory.get(itemId);
        if (item != null) {
            item.setQuantity(newQuantity);
            System.out.println("🔄 Updated " + item.getName() + " to quantity: " + newQuantity);
            
            // This calls the trigger logic we wrote on Wednesday
            checkLowStock(item); 
        } else {
            System.err.println("❌ Item ID not found.");
        }
    }
    
}


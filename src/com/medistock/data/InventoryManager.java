package com.medistock.data;

import com.medistock.service.StockAlerter;
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

    public InventoryManager() {
        this.inventory = new HashMap<>();
        this.parser = new CSVParser();
        this.alerter = new StockAlerter();
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
            }
            System.out.println("✅ Inventory Manager updated: " + inventory.size() + " items loaded.");
        } catch (Exception e) {
            System.err.println("❌ Failed to load inventory: " + e.getMessage());
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
    
}


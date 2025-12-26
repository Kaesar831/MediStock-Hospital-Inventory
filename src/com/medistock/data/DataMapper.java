package com.medistock.data;

import com.medistock.model.InventoryItem;
import com.medistock.model.Medication;
import com.medistock.model.Supplier;
import com.medistock.model.HospitalWard;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DataMapper {
	
	// Placeholder data for fields not present in simple CMS datasets
	private static final Supplier DEFAULT_SUPPLIER = new Supplier("SUP001", "Global Pharma Inc.", "J. Doe", "555-1234");
	private static final HospitalWard DEFAULT_WARD = new HospitalWard("WARD01", "Central Pharmacy", "Pharmacy", 500);
	
	/**
	 * Converts a single CSV row (String[]) into Medication object.
	 * This method handles type conversion and cleaning.
	 */
	public static InventoryItem mapRowToItem(String[] row, int rowIndex) {
		
		// ---1. Data Cleaning and Type Conversion ---
		
		// Convert String to double/int, handling potential errors and cleaning currency symbols
		int quantity = 0;
		try {
			// Clean up possible commas or currency symbols before parsing
			String quantityStr = row[5].trim().replaceAll(",", "");
			quantity = (int) Double.parseDouble(quantityStr);
		} catch (NumberFormatException e) {
			System.err.println("Skipping row " + rowIndex + ": Invalid quantity value: " + row[5]);
			return null; // Skip invalid row
		}
		
		double unitCost = 0.0;
		try {
			//Clean up currency symbols ($) if present
			String costStr = row[8].trim().replaceAll("$", "");
			unitCost = Double.parseDouble(costStr);
		} catch (NumberFormatException e) {
			System.err.println("Skipping row " + rowIndex + ": Invalid cost value: " + row[8]);
			return null; // Skip invalid row
		}
		
		// Generate ID and placeholder expiration date (as CMS data often lacks date)
		String itemId = "INV-" + rowIndex;
		String batchNumber = "BATCH-" + rowIndex;
		String ndcCode = "NDC" + rowIndex;
		String dosageForm = "Pills";
		
		// Set a placeholder expiration date: e.g., 3 years from today
        LocalDate expirationDate = LocalDate.now().plusYears(3).minusDays(rowIndex % 365);
        
        // --- 2. Object Instantiation (Medication) ---
        // Simple logic to determine if it's a controlled substance (e.g., if dosage form contains 'OXY' or 'MOR')
        boolean isControlled = row[2].toUpperCase().contains("OXY") || row[2].toUpperCase().contains("MOR"); 

        // Instantiate the Medication object
        return new Medication(
            itemId, 
            row[1], // Drug Name
            quantity, 
            unitCost, 
            expirationDate, 
            DEFAULT_SUPPLIER, 
            DEFAULT_WARD,
            batchNumber,
            dosageForm,
            ndcCode,
            isControlled
            );	
	}
}

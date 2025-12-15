package com.medistock.data;

import com.medistock.model.InventoryItem;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
	
	/**
	 * Reads the entire contents of the CSV file using openCSV.
	 * @param filePath The path to the CMS/Kaggle CSV file.
	 * @return A List of String arrays, where each array is a row from the CSV.  
	 */
	
	// Changed return type to InventoryList
	
	public List<InventoryItem> readData(String filePath) throws IOException, CsvException {
			List<InventoryItem> inventoryList = new ArrayList<>();
			
			// Use a simple BufferedReader logic for efficient row-by-row processing 
	        // OR use the existing CSVReader.readAll() followed by a loop:
	        
	        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\cresu\\OneDrive\\Documents\\PGCC_Computer_Science\\INT 2200 Programming in Java\\Medicare Part D Drug Spending.csv"))) {
	            List<String[]> allRows = reader.readAll();
	            
	            // Skip the header row (index 0)
	            for (int i = 1; i < allRows.size(); i++) {
	                String[] row = allRows.get(i);
	                
	                // --- MAPPING STEP ---
	                InventoryItem item = DataMapper.mapRowToItem(row, i);
	                
	                if (item != null) {
	                    inventoryList.add(item);
	                }
	            }
	            
	            System.out.println("✅ Successfully mapped " + inventoryList.size() + " items to objects.");
	            
	        } // The exception handling is already present in the method signature

	        return inventoryList;
	}

}

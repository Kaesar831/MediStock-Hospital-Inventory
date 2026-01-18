package com.medistock.repository;

import com.medistock.model.HospitalWard;
import com.medistock.model.Medication;
import com.medistock.model.Supplier;
import com.medistock.util.SecurityLogger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class InventoryDAO {

    /**
     * Exports all Java Medication objects to the SQL database using Batch processing.
     * @param userRole 
     */
    public void exportMedications(List<Medication> inventory, String userRole) {
        String sql = "INSERT OR REPLACE INTO medications (id, name, quantity, unit_cost, expiry_date, " +
                     "supplier_id, ward_id, batch_number, dosage, ndc_code, is_controlled, is_generic) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false); // Start transaction for performance

            for (Medication med : inventory) {
                pstmt.setString(1, med.getItemId());
                pstmt.setString(2, med.getName());
                pstmt.setInt(3, med.getQuantity());
                pstmt.setDouble(4, med.getUnitCost());
                pstmt.setString(5, med.getExpirationDate().toString());
                pstmt.setString(6, med.getSupplier().getSupplierId());
                pstmt.setString(7, med.getLocation().getWardId());
                pstmt.setString(8, med.getBatchNumber());
                pstmt.setString(9, med.getDosageForm());
                pstmt.setString(10, med.getNdcCode());
                pstmt.setInt(11, med.isControlledSubstance() ? 1 : 0);
                pstmt.setInt(12, med.isGeneric() ? 1 : 0);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit(); // Finalize save
            System.out.println("✅ Batch Export Complete: " + inventory.size() + " items saved.");
            
        } catch (SQLException e) {
            System.err.println("❌ Export Error: " + e.getMessage());
        }
        
        try {
            // ... (after successful conn.commit())
            SecurityLogger.logAction(userRole, "DATABASE_SYNC", 
                "Batch export of " + inventory.size() + " items.");
        } catch (Exception e) {
            SecurityLogger.logAction(userRole, "ERROR", "Failed sync: " + e.getMessage());
        }
    }
    
    public List<Medication> importMedications() {
        List<Medication> list = new ArrayList<>();
        String sql = "SELECT * FROM medications";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Reconstructing the objects
                Supplier tempSup = new Supplier(rs.getString("supplier_id"), "Imported Supplier", "N/A", "000");
                HospitalWard tempWard = new HospitalWard(rs.getString("ward_id"), "Imported Ward", "General", 100);

                Medication med = new Medication(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_cost"),
                    LocalDate.parse(rs.getString("expiry_date")),
                    tempSup,
                    tempWard,
                    rs.getString("batch_number"),
                    rs.getString("dosage"), // dosageForm
                    rs.getString("ndc_code"),
                    rs.getInt("is_controlled") == 1,
                    rs.getInt("is_generic") == 1
                );
                list.add(med);
            }
        } catch (SQLException e) {
            System.err.println("❌ Import Error: " + e.getMessage());
        }
        return list;
    }
    /**
     * REPORT 1: Low Stock Alert
     */
    public void printLowStockReport(int threshold) {
        String sql = "SELECT name, quantity FROM medications WHERE quantity < ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, threshold);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n--- ⚠️ LOW STOCK REPORT (Threshold: " + threshold + ") ---");
            while (rs.next()) {
                System.out.println("Item: " + rs.getString("name") + " | Qty: " + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL Report Error: " + e.getMessage());
        }
    }

    /**
     * REPORT 2: Top High-Cost Items
     */
    public void printHighValueReport(int limit) {
        String sql = "SELECT name, unit_cost FROM medications ORDER BY unit_cost DESC LIMIT ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n--- 💰 TOP " + limit + " HIGHEST COST ITEMS ---");
            while (rs.next()) {
                System.out.printf("Item: %-20s | Unit Cost: $%.2f%n", rs.getString("name"), rs.getDouble("unit_cost"));
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL Report Error: " + e.getMessage());
        }
    }

    /**
     * REPORT 3: Total Potential Reimbursement (SQL Logic)
     */
    public void printTotalReimbursementReport() {
        // SQL CASE statement mirrors your Java calculateReimbursement() logic
        String sql = "SELECT SUM(CASE WHEN is_generic = 1 THEN (quantity * unit_cost) * 1.08 " +
                     "ELSE (quantity * unit_cost) * 1.06 END) as total_reimbursement FROM medications";
        
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                double total = rs.getDouble("total_reimbursement");
                System.out.println("\n--- 🏥 TOTAL ESTIMATED HOSPITAL REIMBURSEMENT ---");
                System.out.printf("Total Expected: $%.2f%n", total);
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL Report Error: " + e.getMessage());
        }
    }
    
    /**
     * Creates a clean CSV file specifically for Tableau/PowerBI visualization.
     */
    public void exportForTableau(String filename) {
        String sql = "SELECT name, quantity, unit_cost, is_generic, expiry_date FROM medications";
        
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            
            // 1. Write the Header Row
            writer.println("ItemName,StockLevel,Cost,ReimbursementPotential,ExpirationDate");
            
            // 2. Process and Write Data
            while (rs.next()) {
                String name = rs.getString("name");
                int qty = rs.getInt("quantity");
                double cost = rs.getDouble("unit_cost");
                int isGeneric = rs.getInt("is_generic");
                String expiry = rs.getString("expiry_date");
                
                // Calculate Reimbursement based on business logic
                double factor = (isGeneric == 1) ? 1.08 : 1.06;
                double reimbursement = (qty * cost) * factor;
                
                // Write formatted row
                writer.printf("%s,%d,%.2f,%.2f,%s%n", 
                              name, qty, cost, reimbursement, expiry);
            }
            
            System.out.println("📊 Tableau Export Successful: " + filename);
            
        } catch (SQLException | IOException e) {
            System.err.println("❌ Export Failed: " + e.getMessage());
        }
    }
}
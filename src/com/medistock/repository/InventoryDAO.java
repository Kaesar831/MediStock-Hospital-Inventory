package com.medistock.repository;

import com.medistock.model.HospitalWard;
import com.medistock.model.Medication;
import com.medistock.model.Supplier;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    /**
     * Exports all Java Medication objects to the SQL database using Batch processing.
     */
    public void exportMedications(List<Medication> inventory) {
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
}
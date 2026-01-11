package com.medistock.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    // The database file will be created in your project root
    private static final String DB_URL = "jdbc:sqlite:medistock.db";
    private static Connection connection = null;

    /**
     * Returns the active database connection (Singleton).
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the driver class
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("✅ Database Connected Successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ SQLite Driver not found: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Initializes the database tables if they don't exist.
     */
    public static void initializeDatabase() {
        // 1. Create Suppliers Table
        String createSuppliers = "CREATE TABLE IF NOT EXISTS suppliers (" +
                "supplier_id TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "contact_person TEXT, " +
                "phone_number TEXT" +
                ");";

        // 2. Create Wards Table
        String createWards = "CREATE TABLE IF NOT EXISTS wards (" +
                "ward_id TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "department TEXT, " +
                "capacity INTEGER" +
                ");";

        // 3. Create Medications Table (links to both above)
        String createMedications = "CREATE TABLE IF NOT EXISTS medications (" +
                "id TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "quantity INTEGER, " +
                "unit_cost REAL, " +
                "expiry_date TEXT, " +
                "supplier_id TEXT, " +
                "ward_id TEXT, " +
                "batch_number TEXT, " +
                "dosage TEXT, " +
                "ndc_code TEXT, " +
                "is_controlled INTEGER, " + // SQLite uses 0/1 for booleans
                "is_generic INTEGER, " +
                "FOREIGN KEY(supplier_id) REFERENCES suppliers(supplier_id), " +
                "FOREIGN KEY(ward_id) REFERENCES wards(ward_id)" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Execute in order
            stmt.execute(createSuppliers);
            stmt.execute(createWards);
            stmt.execute(createMedications);
            
            System.out.println("✅ All MediStock tables verified/created.");
        } catch (SQLException e) {
            System.err.println("❌ Schema Creation Error: " + e.getMessage());
        }
    }
}
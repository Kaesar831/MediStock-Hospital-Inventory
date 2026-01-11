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
        String createTableSQL = "CREATE TABLE IF NOT EXISTS inventory (" +
                                "id TEXT PRIMARY KEY, " +
                                "name TEXT, " +
                                "quantity INTEGER, " +
                                "unit_cost REAL, " +
                                "expiry_date TEXT, " +
                                "supplier_id TEXT, " +
                                "ward_id TEXT" +
                                ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("✅ Inventory table verified/created.");
        } catch (SQLException e) {
            System.err.println("❌ Database Init Error: " + e.getMessage());
        }
    }
}
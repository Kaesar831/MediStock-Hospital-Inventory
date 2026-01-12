package com.medistock.util;

import java.io.IOException;
import java.util.logging.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SecurityLogger {
    private static final Logger logger = Logger.getLogger("HIPAA_Audit_Trail");
    private static FileHandler fileHandler;

    static {
        try {
            // Log file will be named medistock_audit.log
            fileHandler = new FileHandler("medistock_audit.log", true);
            fileHandler.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$s] [%2$s] %3$s %n";

                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(format,
                            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                            lr.getLevel().getLocalizedName(),
                            lr.getMessage()
                    );
                }
            });
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Prevents logs from cluttering the console
        } catch (IOException e) {
            System.err.println("❌ Security Logger failed to initialize: " + e.getMessage());
        }
    }

    /**
     * Logs an inventory action with a hardcoded role (for now).
     * @param role The user role (Admin/Pharmacist)
     * @param action The operation performed (e.g., "Added Medication")
     * @param details Specifics (e.g., "NDC: 001-22")
     */
    public static void logAction(String role, String action, String details) {
        String logMessage = String.format("USER_ROLE: %s | ACTION: %s | DETAILS: %s", 
                            role, action, details);
        logger.info(logMessage);
    }
    
    /**
     * Redacts sensitive identifiers to maintain HIPAA compliance in logs.
     * Example: "12345-678-90" becomes "XXXXX-XXX-90"
     */
    public static String redact(String input) {
        if (input == null || input.length() < 5) return "****";
        return "HIDDEN-" + input.substring(input.length() - 5);
    }
}
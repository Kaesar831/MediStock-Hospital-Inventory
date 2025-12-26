package com.medistock.main;

import com.medistock.data.InventoryManager;
import com.medistock.model.InventoryItem;

import java.util.Scanner;

public class mediStockApp {
	public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("========================================");
        System.out.println("   MediStock Hospital Inventory System  ");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Load Data from CMS CSV");
            System.out.println("2. View Total Inventory Count");
            System.out.println("3. Exit");
            System.out.println("4. Check for Expiring Items");
            System.out.println("5. Update Item Quantity");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter full path to CSV file: ");
                    String path = scanner.nextLine();
                    manager.loadInventoryFromCSV(path);
                    break;
                case 2:
                    System.out.println("📊 Current Total Items: " + manager.getInventoryCount());
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting MediStock. Goodbye!");
                    break;
                case 4:
                    System.out.print("Enter days threshold (e.g., 30 for next month): ");
                    int days = scanner.nextInt();
                    var expiring = manager.getAlerter().getExpiringSoon(days);
                    System.out.println("⚠️ URGENT: " + expiring.size() + " items expiring soon!");
                    expiring.forEach(item -> System.out.println(" - " + item.getName() + " Exp: " + item.getExpirationDate()));
                    break;
                 // Inside the while loop switch statement:
                case 5:
                    System.out.print("Enter Item ID to update (e.g., INV-1): ");
                    String idToUpdate = scanner.nextLine();
                    System.out.print("Enter NEW quantity (try '5' to trigger alert): ");
                    int newQty = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    manager.updateItemQuantity(idToUpdate, newQty);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
	

}

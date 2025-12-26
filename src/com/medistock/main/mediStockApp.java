package com.medistock.main;

import com.medistock.data.InventoryManager;
import com.medistock.model.InventoryItem;
import com.medistock.model.Medication;

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
            System.out.println("6. View Financial/Reimbursement Report");
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
                case 5:
                    System.out.print("Enter Item ID to update (e.g., INV-1): ");
                    String idToUpdate = scanner.nextLine();
                    System.out.print("Enter NEW quantity (try '5' to trigger alert): ");
                    int newQty = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    manager.updateItemQuantity(idToUpdate, newQty);
                    break;
                case 6:
                    System.out.print("Enter Item ID for Financial Report: ");
                    String finId = scanner.nextLine().toUpperCase();
                    InventoryItem selected = manager.getAllItems().get(finId);

                    if (selected instanceof Medication) {
                        Medication med = (Medication) selected; // Cast to Medication to access subclass methods
                        System.out.println("--- FINANCIAL REPORT ---");
                        System.out.println("Drug: " + med.getName());
                        System.out.printf("Current Inventory Value: $%.2f%n", (med.getUnitCost() * med.getQuantity()));
                        System.out.println("--------------------------------------");
                        
                        // Use the new methods we standardized with constants
                        System.out.printf("Medicare Payout (ASP+6%%):   $%.2f%n", med.calculateReimbursement());
                        System.out.printf("Estimated 340B Savings:      $%.2f%n", 
                                          ((med.getUnitCost() * med.getQuantity()) - med.calculate340BCost()));
                    } else {
                        System.out.println("❌ Financial reports are currently only available for Medication items.");
                    }
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
	

}

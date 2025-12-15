package com.medistock.main;

import com.medistock.data.InventoryManager;
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
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

}

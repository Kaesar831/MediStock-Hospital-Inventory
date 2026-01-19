# MediStock – Real-Time Hospital Inventory Tracker

**MediStock** is a Java-based inventory management system designed for healthcare providers. It integrates real-world CMS (Medicare/Medicaid) data to track medication stock levels, expiration dates, and reimbursement logic.

## 🚀 Project Impact
- **Keyword Hits:** Java, OOP, SQL, HIPAA Compliance, Medicare/Medicaid Reimbursement.
- **Goal:** Provide a HIPAA-compliant dashboard for pharmacy informatics and supply chain analysts.

## 📅 Progress Tracker

### Week 1: Foundation & Data Ingestion (COMPLETED)
- [x] **Core OOP Architecture:** Implemented `InventoryItem` parent class with `Medication` and `MedicalDevice` inheritance.
- [x] **Maven Integration:** Configured project dependencies using Maven.
- [x] **Data Parsing:** Integrated **OpenCSV** to handle large-scale CMS Medicare Part D datasets.
- [x] **Data Mapping:** Built a robust `DataMapper` to clean raw CSV strings and convert them into Java Objects.
- [x] **Inventory Manager:** Developed a `HashMap` based management system for $O(1)$ lookup performance.
- [x] **Console UI:** Built a functional command-line interface for data loading and reporting.

## 🛠️ Technical Stack
- **Language:** Java 17+
- **Build Tool:** Maven
- **Libraries:** OpenCSV
- **Data Source:** CMS.gov Medicare Part D Prescriber Data
### Week 2: Intelligent Monitoring & Financial Logic (COMPLETED)
- [x] **Expiration Engine:** Implemented `PriorityQueue` for $O(1)$ access to most urgent expiring medications.
- [x] **Observer Pattern:** Built a decoupled notification system (`StockNotifier`) with Email and SMS stubs.
- [x] **Automated Alerts:** Integrated low-stock triggers that broadcast messages to multiple observers.
- [x] **CMS Reimbursement Logic:** Developed calculations for **Medicare Part B (ASP + 6%)** and **340B Drug Pricing**.
- [x] **Unit Testing:** Achieved high test coverage for financial logic and data structures using **JUnit 5**.

## 📊 Data Visualization & Analytics
The MediStock system exports clean CSV data for executive oversight. 

### Interactive Dashboard
You can view the live interactive dashboard here:
[👉 View MediStock Executive Dashboard on Tableau Public](https://public.tableau.com/authoring/MediStock_Executive_Dashboard/Dashboard1#1)

**Key Metrics Tracked:**
* **Real-time Stock Levels:** Identified by Red-Green urgency mapping.
* **Financial Risk:** Analysis of high-cost inventory batches.
* **Reimbursement Potential:** Predicted Medicare/340B payouts based on current stock.
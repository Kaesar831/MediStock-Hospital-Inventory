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
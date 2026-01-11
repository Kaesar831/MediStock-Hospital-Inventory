package test.java.com.medistock.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.medistock.model.Medication;
import com.medistock.model.HospitalWard;
import com.medistock.model.Supplier;
import java.time.LocalDate;

public class ReimbursementTest {

    @Test
    public void testBrandedReimbursement() {
    	// Setup: $100 unit cost, 10 units, Branded (1.06 factor)
    	// 1. Instantiate the HospitalWard class with its 4 required attributes
        // (wardId, name, department, capacity)
        HospitalWard pharmacyWard = new HospitalWard("W-101", "Main Pharmacy", "General", 500);
        Supplier pharmiCo = new Supplier("S-99", "PharmiCo Ltd", "Jane Doe", "555-0123");

        // 2. Pass that object into the Medication constructor
        // Note: Ensure the 7th parameter is 'pharmacyWard' (not a String)
        Medication med = new Medication("TEST-1", "Branded Drug", 10, 100.0, LocalDate.now(), pharmiCo, pharmacyWard, "B1", "Tablet", "001", false, false
        );
        
        // Expected: (10 * 100) * 1.06 = 1060.0
        assertEquals(1060.0, med.calculateReimbursement(), 0.001);
    }
    
    @Test
    public void testGenericReimbursement() {
        // Setup: $100 unit cost, 10 units, Generic (1.08 factor)
    	HospitalWard pharmacyWard = new HospitalWard("W-101", "Main Pharmacy", "General", 500);
        Supplier pharmiCo = new Supplier("S-99", "PharmiCo Ltd", "Jane Doe", "555-0123");
        Medication genericMed = new Medication("ID-2", "Ibuprofen", 10, 100.0, 
            LocalDate.now(), pharmiCo, pharmacyWard, "B2", "Tablet", "456", false, true);
        
        // Calculation: (100 * 10) * 1.08 = 1080.0
        assertEquals(1080.0, genericMed.calculateReimbursement(), 0.001);
    }
        
    
}

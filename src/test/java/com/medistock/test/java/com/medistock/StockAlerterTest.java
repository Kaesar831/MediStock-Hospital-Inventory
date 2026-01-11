package test.java.com.medistock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.medistock.model.Medication;
import com.medistock.model.HospitalWard;
import com.medistock.model.Supplier;
import com.medistock.service.StockAlerter;
import java.time.LocalDate;

public class StockAlerterTest {

    @Test
    public void testExpirationPriority() {
        // 1. Setup Dependencies
        HospitalWard ward = new HospitalWard("W1", "Pharmacy", "Main", 100);
        Supplier supplier = new Supplier("S1", "MedCorp", "John", "555-0100");
        
        // 2. Create the Alerter (The PriorityQueue wrapper)
        StockAlerter alerter = new StockAlerter();

        // 3. Create a medication expiring in 10 days
        Medication farMed = new Medication("ID-FAR", "Distant Med", 10, 5.0, 
            LocalDate.now().plusDays(10), supplier, ward, "B1", "Tab", "001", false, false);
            
        // 4. Create a medication expiring TOMORROW (Most Urgent)
        Medication soonMed = new Medication("ID-SOON", "Urgent Med", 5, 10.0, 
            LocalDate.now().plusDays(1), supplier, ward, "B2", "Tab", "002", false, false);

        // 5. Add to queue
        alerter.addItem(farMed);
        alerter.addItem(soonMed);

        // 6. Assertion: The "peek" of the queue should be the Urgent Med
        // This proves your PriorityQueue is sorting correctly by date!
        assertEquals("Urgent Med", alerter.getMostUrgentItem().getName());
    }
}
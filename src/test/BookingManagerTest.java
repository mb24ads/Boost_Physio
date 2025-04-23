package test;

import manager.BookingManager;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingManagerTest {

    private BookingManager bookingManager;
    private Patient patient1;
    private Physiotherapist physiotherapist1;
    private Treatment treatment1;

    // Utility method to reset Singleton instance
    private void resetBookingManagerSingleton() {
        try {
            java.lang.reflect.Field instance = BookingManager.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null); // Reset the instance
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset BookingManager singleton", e);
        }
    }

    @BeforeEach
    public void setUp() {
        resetBookingManagerSingleton(); // Reset before each test
        bookingManager = BookingManager.getInstance(); // Get fresh instance

        // Create a patient
        patient1 = new Patient("P001", "John Doe", "123 Main St", "555-1234");

        // Create a physiotherapist with a list of treatments
        List<String> treatments = Arrays.asList("Massage Therapy", "Physiotherapy");
        physiotherapist1 = new Physiotherapist("PT001", "Dr. Danial Andrew", "General Physio", "555-9876", treatments);

        // Add patient and physiotherapist to the system
        bookingManager.addPatient(patient1);
        bookingManager.addPhysiotherapist(physiotherapist1);

        // Create a treatment for the patient
        treatment1 = new Treatment("T001", LocalDateTime.of(2025, 5, 10, 10, 30), physiotherapist1, "Massage Therapy");
        treatment1.setPatient(patient1);

        // Add treatment to the system
        bookingManager.addTreatment(treatment1);
    }


    @Test
    public void testPrintFinalReport() {
        // Cancel the treatment first
        bookingManager.cancelTreatment("T001", patient1);

        // Capture the output of the final report (Optional: Redirect System.out to capture it)
        // Here, we'll just test that the method runs without throwing an exception
        assertDoesNotThrow(() -> bookingManager.printFinalReport());
    }

    @Test
    public void testFindPatientById() {
        // Find the patient by ID and verify it's correct
        Patient foundPatient = bookingManager.findPatientById("P001");
        assertNotNull(foundPatient);
        assertEquals("John Doe", foundPatient.getName());
    }

    @Test
    public void testFindPhysiotherapistByName() {
        // Find the physiotherapist by name and verify it's correct
        Physiotherapist foundPhysio = bookingManager.findPhysiotherapistByName("Dr. Danial Andrew");
        assertNotNull(foundPhysio);
        assertEquals("Dr. Danial Andrew", foundPhysio.getName());
    }

    @Test
    public void testAddPatient() {
        // Add a new patient and check if the patient is added
        Patient newPatient = new Patient("P002", "Jane Smith", "456 Oak St", "555-5678");
        bookingManager.addPatient(newPatient);
        assertTrue(bookingManager.getAllPatients().contains(newPatient));
    }

    @Test
    public void testRemovePatient() {
        // Remove the patient and verify that the patient was removed
        bookingManager.removePatient("P001");
        assertNull(bookingManager.findPatientById("P001"));
    }
}

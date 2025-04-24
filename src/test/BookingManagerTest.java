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
    private BookingManager manager;
    private Patient patient1;
    private Patient patient2;
    private Physiotherapist physio1;
    private Treatment treatment1;
    private Treatment treatment2;

    @BeforeEach
    public void setup() {
        manager = BookingManager.getInstance();

        // Clear any existing data before each test
        manager.getAllPatients().clear();
        manager.getAllPhysiotherapists().clear();
        manager.getCanceledTreatments().clear();

        // Create patients
        patient1 = new Patient("P001", "John Doe", "123 Street", "1234567890");
        patient2 = new Patient("P002", "Jane Smith", "456 Road", "9876543210");

        // Create a physiotherapist with the correct constructor
        physio1 = new Physiotherapist(
                "PT001",
                "Dr. Danial Andrew",
                "Back Pain",
                "0123456789",
                Arrays.asList("Monday", "Wednesday", "Friday")
        );

        // Create treatments using LocalDateTime
        treatment1 = new Treatment("Back Therapy", LocalDateTime.of(2025, 5, 10, 10, 0), physio1, "T001");
        treatment2 = new Treatment("Shoulder Therapy", LocalDateTime.of(2025, 5, 11, 11, 0), physio1, "T002");

        // Assign treatments to physiotherapist
        physio1.addTreatment(treatment1);
        physio1.addTreatment(treatment2);

        // Register patients and physiotherapist with BookingManager
        manager.addPatient(patient1);
        manager.addPatient(patient2);
        manager.addPhysiotherapist(physio1);
        manager.addTreatment(treatment1);
        manager.addTreatment(treatment2);

        // Book treatment1 for patient1
        treatment1.book(patient1);  // Instead of directly setting the status
    }



    @Test
    public void testCancelTreatmentNotBooked() {
        boolean result = manager.cancelTreatment("T002", patient1); // Not booked yet
        assertFalse(result);
        assertEquals(AppointmentStatus.AVAILABLE, treatment2.getStatus());
    }



    @Test
    public void testRebookTreatmentFailsIfNotCanceled() {
        boolean result = manager.rebookTreatment("T001", patient2); // Already booked
        assertFalse(result);
    }

    @Test
    public void testAddAndFindPatient() {
        Patient found = manager.findPatientById("P001");
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
    }

    @Test
    public void testRemovePatient() {
        manager.removePatient("P001");
        assertNull(manager.findPatientById("P001"));
    }

    @Test
    public void testIsBookingIdUsed() {
        assertTrue(manager.isBookingIdUsed("T001"));
        assertFalse(manager.isBookingIdUsed("T999"));
    }



    @Test
    public void testGetCanceledTreatmentsList() {
        manager.cancelTreatment("T001", patient1);
        List<CanceledTreatmentRecord> list = manager.getCanceledTreatments();
        assertEquals(1, list.size());
    }
}

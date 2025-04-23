package test;

import model.Physiotherapist;
import model.Patient;
import model.Treatment;
import model.AppointmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PhysiotherapistTest {

    private Physiotherapist physio;
    private Treatment treatment1;
    private Treatment treatment2;
    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        // Create a physiotherapist
        physio = new Physiotherapist("P001", "Dr. Danial Andrew", "123 Main St", "555-1234", Arrays.asList("Orthopedics", "Sports Therapy"));

        // Create patients (assuming Patient constructor requires id, name, address, and phone)
        patient1 = new Patient("ID001", "John Doe", "456 Oak St", "555-5678");
        patient2 = new Patient("ID002", "Jane Smith", "789 Pine St", "555-9876");

        // Create treatments with booking IDs
        treatment1 = new Treatment("Back Therapy", LocalDateTime.now(), physio, "BKG001");
        treatment2 = new Treatment("Shoulder Therapy", LocalDateTime.now().plusHours(1), physio, "BKG002");
    }

    @Test
    void testConstructor() {
        // Verify physiotherapist information
        assertNotNull(physio, "Physiotherapist should be created successfully.");
        assertEquals("Dr. Danial Andrew", physio.getName(), "Physiotherapist name should match.");
        assertEquals("123 Main St", physio.getAddress(), "Physiotherapist address should match.");
        assertEquals("555-1234", physio.getPhone(), "Physiotherapist phone number should match.");
    }

    @Test
    void testTreatmentBooking() {
        // Test booking a treatment
        treatment1.book(patient1);
        assertEquals(AppointmentStatus.BOOKED, treatment1.getStatus(), "Treatment should be booked.");
        assertEquals(patient1, treatment1.getPatient(), "The patient should be correctly assigned.");
    }

    @Test
    void testTreatmentCancel() {
        // Book the treatment first
        treatment1.book(patient1);

        // Now cancel the treatment
        treatment1.cancel(patient1);
        assertEquals(AppointmentStatus.CANCELLED, treatment1.getStatus(), "Treatment status should be CANCELLED.");
        assertEquals(patient1.getName(), treatment1.getCanceledBy(), "The cancelation should be attributed to the correct patient.");
        assertNull(treatment1.getPatient(), "The patient should be null after cancellation.");
    }

    @Test
    void testTreatmentAttend() {
        // Book the treatment first
        treatment1.book(patient1);

        // Now attend the treatment
        treatment1.attend();
        assertEquals(AppointmentStatus.ATTENDED, treatment1.getStatus(), "Treatment status should be ATTENDED.");
    }

    @Test
    void testTreatmentToString() {
        // Convert the treatment to a string and verify its format
        String expectedString = String.format("🟢 AVAILABLE - Back Therapy - %s, %s-%s by Dr. Danial Andrew (Booking ID: BKG001)",
                treatment1.getDateTime().format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
                treatment1.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                treatment1.getDateTime().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm")));

        String actualString = treatment1.toString();
        assertTrue(actualString.contains("Back Therapy"), "Treatment string should contain the treatment name.");
        assertTrue(actualString.contains("AVAILABLE"), "Treatment string should show the status.");
        assertTrue(actualString.contains("Dr. Danial Andrew"), "Treatment string should contain the physiotherapist's name.");
        assertTrue(actualString.contains("BKG001"), "Treatment string should contain the booking ID.");
    }

    @Test
    void testTreatmentStatus() {
        // Verify initial status of a treatment
        assertEquals(AppointmentStatus.AVAILABLE, treatment1.getStatus(), "Treatment should be AVAILABLE initially.");

        // Change status of treatment to BOOKED
        treatment1.setStatus(AppointmentStatus.BOOKED);
        assertEquals(AppointmentStatus.BOOKED, treatment1.getStatus(), "Treatment status should be BOOKED after update.");
    }
}

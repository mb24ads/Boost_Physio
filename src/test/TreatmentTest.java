package test;

import model.AppointmentStatus;
import model.Patient;
import model.Physiotherapist;
import model.Treatment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TreatmentTest {

    private Physiotherapist physiotherapist;
    private Treatment treatment;
    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        // Create a physiotherapist
        physiotherapist = new Physiotherapist("P001", "Dr. Danial Andrew", "123 Main St", "555-1234", Arrays.asList("Orthopedics", "Sports Therapy"));

        // Create patients
        patient1 = new Patient("ID001", "John Doe", "456 Oak St", "555-5678");
        patient2 = new Patient("ID002", "Jane Smith", "789 Pine St", "555-9876");

        // Create a treatment with a booking ID
        treatment = new Treatment("Back Therapy", LocalDateTime.now(), physiotherapist, "BKG001");
    }

    @Test
    void testConstructor() {
        assertNotNull(treatment, "Treatment object should be created.");
        assertEquals("Back Therapy", treatment.getName(), "Treatment name should match.");
        assertEquals(AppointmentStatus.AVAILABLE, treatment.getStatus(), "Treatment status should initially be AVAILABLE.");
        assertNull(treatment.getPatient(), "Patient should be null initially.");
        assertNull(treatment.getCanceledBy(), "Cancellation info should be null initially.");
    }

    @Test
    void testBookTreatment() {
        // Book the treatment
        treatment.book(patient1);

        // Verify the treatment status and patient assignment
        assertEquals(AppointmentStatus.BOOKED, treatment.getStatus(), "Treatment should be marked as BOOKED.");
        assertEquals(patient1, treatment.getPatient(), "Patient should be assigned to the treatment.");
    }

    @Test
    void testCancelTreatment() {
        // Book and then cancel the treatment
        treatment.book(patient1);
        treatment.cancel(patient1);

        // Verify cancellation
        assertEquals(AppointmentStatus.CANCELLED, treatment.getStatus(), "Treatment status should be CANCELLED after cancellation.");
        assertEquals(patient1.getName(), treatment.getCanceledBy(), "The canceledBy field should be set to the patient's name.");
        assertNull(treatment.getPatient(), "Patient should be null after cancellation.");
    }

    @Test
    void testAttendTreatment() {
        // Book and then attend the treatment
        treatment.book(patient1);
        treatment.attend();

        // Verify attendance
        assertEquals(AppointmentStatus.ATTENDED, treatment.getStatus(), "Treatment status should be ATTENDED after attending.");
    }

    @Test
    void testAttendTreatmentWithoutBooking() {
        // Attempt to attend the treatment without booking
        treatment.attend();

        // Verify that attending without booking does not change the status
        assertEquals(AppointmentStatus.AVAILABLE, treatment.getStatus(), "Treatment status should not change if not booked.");
    }

    @Test
    void testToString() {
        // Convert the treatment to string and verify the format
        String expectedString = String.format("🟢 AVAILABLE - Back Therapy - %s, %s-%s by Dr. Danial Andrew (Booking ID: BKG001)",
                treatment.getDateTime().format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
                treatment.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                treatment.getDateTime().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm")));

        String actualString = treatment.toString();
        assertTrue(actualString.contains("Back Therapy"), "Treatment string should contain the treatment name.");
        assertTrue(actualString.contains("AVAILABLE"), "Treatment string should show the status.");
        assertTrue(actualString.contains("Dr. Danial Andrew"), "Treatment string should contain the physiotherapist's name.");
        assertTrue(actualString.contains("BKG001"), "Treatment string should contain the booking ID.");
    }

    @Test
    void testCancelTreatmentByNonBookedPatient() {
        // Attempt to cancel the treatment without booking
        treatment.cancel(patient2);

        // Verify that the status doesn't change and patient is not canceled
        assertEquals(AppointmentStatus.AVAILABLE, treatment.getStatus(), "Treatment should remain AVAILABLE as it was not booked.");
        assertNull(treatment.getCanceledBy(), "The canceledBy field should remain null.");
    }

    @Test
    void testMultipleBookingAndCancellation() {
        // Book the treatment by patient1
        treatment.book(patient1);
        assertEquals(AppointmentStatus.BOOKED, treatment.getStatus(), "Treatment should be BOOKED after booking.");

        // Cancel the treatment by patient1
        treatment.cancel(patient1);
        assertEquals(AppointmentStatus.CANCELLED, treatment.getStatus(), "Treatment should be CANCELLED after cancellation.");

        // Reset the status to AVAILABLE (this might need to be done in the Treatment class if it's not being reset automatically)
        treatment.setStatus(AppointmentStatus.AVAILABLE);  // You may need to add a setStatus method

        // Book the treatment again by patient2
        treatment.book(patient2);
        assertEquals(AppointmentStatus.BOOKED, treatment.getStatus(), "Treatment should be BOOKED again after cancellation.");
    }
}


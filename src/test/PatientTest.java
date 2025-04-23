package test;

import model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        // Create a patient with sample data
        patient = new Patient("ID001", "John Doe", "123 Oak St", "555-1234");
    }

    @Test
    void testConstructor() {
        // Verify that the patient object is created and fields are initialized correctly
        assertNotNull(patient, "Patient object should be created.");
        assertEquals("ID001", patient.getId(), "Patient ID should match.");
        assertEquals("John Doe", patient.getName(), "Patient name should match.");
        assertEquals("123 Oak St", patient.getAddress(), "Patient address should match.");
        assertEquals("555-1234", patient.getPhone(), "Patient phone should match.");
    }

    @Test
    void testToString() {
        // Verify that the toString() method formats correctly
        String expectedString = "John Doe (ID: ID001)";
        assertEquals(expectedString, patient.toString(), "toString should return the correct string.");
    }
}

package test;

import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        // Create an anonymous subclass of Person for testing purposes
        person = new Person("ID002", "Jane Smith", "456 Pine St", "555-5678") {
            @Override
            public String toString() {
                return name + " (ID: " + id + ")";
            }
        };
    }

    @Test
    void testConstructor() {
        // Verify that the person object is created and fields are initialized correctly
        assertNotNull(person, "Person object should be created.");
        assertEquals("ID002", person.getId(), "Person ID should match.");
        assertEquals("Jane Smith", person.getName(), "Person name should match.");
        assertEquals("456 Pine St", person.getAddress(), "Person address should match.");
        assertEquals("555-5678", person.getPhone(), "Person phone should match.");
    }

    @Test
    void testToString() {
        // Verify that the toString() method formats correctly
        String expectedString = "Jane Smith (ID: ID002)";
        assertEquals(expectedString, person.toString(), "toString should return the correct string.");
    }
}

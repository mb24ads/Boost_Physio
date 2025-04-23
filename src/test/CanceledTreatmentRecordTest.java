package test;

import model.CanceledTreatmentRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanceledTreatmentRecordTest {

    @Test
    public void testToString() {
        // Arrange
        String treatmentName = "Massage Therapy";
        String physiotherapistName = "Dr. Danial Andrew";
        String patientName = "John Doe";
        String canceledBy = "Dr. Robert Smith";
        LocalDateTime cancelledOn = LocalDateTime.of(2025, 4, 23, 10, 30, 0, 0);

        CanceledTreatmentRecord record = new CanceledTreatmentRecord(
                treatmentName, physiotherapistName, patientName, canceledBy, cancelledOn
        );

        // Act
        String result = record.toString();

        // Assert
        String expected = "model.CanceledTreatmentRecord@"+Integer.toHexString(record.hashCode());
        assertEquals(expected, result, "toString method should return the correct string.");
    }
}

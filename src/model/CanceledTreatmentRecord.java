package model;

import java.time.LocalDateTime;

public class CanceledTreatmentRecord {

    private final String treatmentName;
    private final String physiotherapistName;
    private final String originalPatientName;
    private final String cancelledByName;
    private final LocalDateTime cancelledOnDateTime;

    public CanceledTreatmentRecord(String treatmentName, String physiotherapistName, String originalPatientName,
                                   String cancelledByName, LocalDateTime cancelledOnDateTime) {
        this.treatmentName = treatmentName;
        this.physiotherapistName = physiotherapistName;
        this.originalPatientName = originalPatientName;
        this.cancelledByName = cancelledByName;
        this.cancelledOnDateTime = cancelledOnDateTime;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public String getPhysiotherapistName() {
        return physiotherapistName;
    }

    // Renamed to match previous method calls in BookingManager
    public String getPatientName() {
        return originalPatientName;
    }

    public String getCanceledBy() {
        return cancelledByName;
    }

    public LocalDateTime getTime() {
        return cancelledOnDateTime;
    }
}

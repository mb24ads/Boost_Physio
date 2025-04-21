package model;

import java.time.LocalDateTime;

public class Treatment {
    private String name;
    private LocalDateTime dateTime;
    private Physiotherapist physiotherapist;
    private Patient patient; // null if not booked
    private AppointmentStatus status;
    private String bookingId; // Unique Booking ID
    private String canceledBy; // Name of the patient who canceled the appointment

    // Constructor
    public Treatment(String name, LocalDateTime dateTime, Physiotherapist physiotherapist, String bookingId) {
        this.name = name;
        this.dateTime = dateTime;
        this.physiotherapist = physiotherapist;
        this.bookingId = bookingId;
        this.status = AppointmentStatus.AVAILABLE;
        this.patient = null;  // No patient is assigned initially
        this.canceledBy = null;  // No cancellation info
    }

    // Getter methods
    public String getName() { return name; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Physiotherapist getPhysiotherapist() { return physiotherapist; }
    public Patient getPatient() { return patient; }
    public AppointmentStatus getStatus() { return status; }
    public String getBookingId() { return bookingId; }
    public String getCanceledBy() { return canceledBy; }

    // Setter methods
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setCanceledBy(String canceledBy) {
        this.canceledBy = canceledBy;
    }

    // Setting the patient for this treatment
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Booking a treatment
    public void book(Patient patient) {
        if (this.status != AppointmentStatus.AVAILABLE) {
            System.out.println("This treatment is already booked or unavailable.");
            return;
        }
        this.status = AppointmentStatus.BOOKED; // Mark as booked
        this.patient = patient; // Assign the patient
        System.out.println("Treatment booked successfully for " + patient.getName());
    }

    // Cancelling a treatment
    public void cancel(Patient cancelingPatient) {
        if (this.patient != null && this.patient.equals(cancelingPatient)) {
            this.patient = null; // Free up the slot
            this.status = AppointmentStatus.CANCELLED; // Mark as canceled
            this.canceledBy = cancelingPatient.getName(); // Track who canceled the treatment
            System.out.println("Treatment canceled by " + cancelingPatient.getName());
        } else {
            System.out.println("Only the booked patient can cancel this treatment.");
        }
    }

    // Attending the treatment
    public void attend() {
        if (status == AppointmentStatus.BOOKED) {
            this.status = AppointmentStatus.ATTENDED;
        } else {
            System.out.println("This treatment cannot be attended as it is not booked.");
        }
    }

    // toString method for treatment details
    @Override
    public String toString() {
        return String.format("%s at %s [%s] by %s (Booking ID: %s)", name, dateTime, status, physiotherapist.getName(), bookingId);
    }
}

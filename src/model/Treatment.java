package model;

import java.time.LocalDateTime;

public class Treatment {
    private String name;
    private LocalDateTime dateTime;
    private Physiotherapist physiotherapist;
    private Patient patient; // null if not booked
    private AppointmentStatus status;

    public Treatment(String name, LocalDateTime dateTime, Physiotherapist physiotherapist) {
        this.name = name;
        this.dateTime = dateTime;
        this.physiotherapist = physiotherapist;
        this.status = AppointmentStatus.AVAILABLE;
    }

    public String getName() { return name; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Physiotherapist getPhysiotherapist() { return physiotherapist; }
    public Patient getPatient() { return patient; }
    public AppointmentStatus getStatus() { return status; }

    public void book(Patient patient) {
        if (this.status != AppointmentStatus.AVAILABLE) {
            System.out.println("This treatment is already booked or unavailable.");
            return;
        }
        this.status = AppointmentStatus.BOOKED; // Mark as booked
        this.patient = patient; // Assign the patient
        System.out.println("Treatment booked successfully for " + patient.getName());
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
        this.patient = null;
        System.out.println("Treatment " + this.getName() + " has been canceled.");
    }

    public void attend() {
        if (status == AppointmentStatus.BOOKED) {
            this.status = AppointmentStatus.ATTENDED;
        }
    }

    @Override
    public String toString() {
        return String.format("%s at %s [%s] by %s", name, dateTime, status, physiotherapist.getName());
    }
}

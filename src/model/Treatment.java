package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        this.status = AppointmentStatus.BOOKED; // Robert as booked
        this.patient = patient; // Assign the patient
        System.out.println("Treatment booked successfully for " + patient.getName());
    }

    // Cancelling a treatment
    public void cancel(Patient cancelingPatient) {
        if (this.patient != null && this.patient.equals(cancelingPatient)) {
            this.patient = null; // Free up the slot
            this.status = AppointmentStatus.CANCELLED; // Robert as canceled
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

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String startTime = dateTime.format(timeFormatter);
        String endTime = dateTime.plusHours(1).format(timeFormatter); // Assuming 1-hour treatment
        String formattedDate = dateTime.format(dateFormatter);

        String statusSymbol;
        switch (status) {
            case AVAILABLE:
                statusSymbol = "🟢 AVAILABLE";
                break;
            case BOOKED:
                statusSymbol = "🔵 BOOKED";
                break;
            case CANCELLED:
                statusSymbol = "🔴 CANCELLED";
                break;
            case ATTENDED:
                statusSymbol = "🟡 ATTENDED";
                break;
            default:
                statusSymbol = "❓ Unknown";
        }

        return String.format("%s - %s, %s %s-%s by %s (Booking ID: %s)",
                statusSymbol,
                name,
                formattedDate,
                startTime,
                endTime,
                physiotherapist.getName(),
                bookingId
        );
    }



}

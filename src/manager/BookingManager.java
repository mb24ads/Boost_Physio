package manager;

import model.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookingManager {
    private static BookingManager instance; // Singleton pattern
    private Map<String, Patient> patients;
    private Map<String, Physiotherapist> physiotherapists;
    private Map<String, Treatment> treatments; // To map booking IDs to treatments
    private Set<String> usedBookingIds; // To track used booking IDs
    //private List<Treatment> canceledTreatments; // To track canceled treatments
    private List<CanceledTreatmentRecord> canceledTreatments;


    private BookingManager() {
        patients = new HashMap<>();
        physiotherapists = new HashMap<>();
        treatments = new HashMap<>();
        usedBookingIds = new HashSet<>();
        canceledTreatments = new ArrayList<>();
    }

    public static BookingManager getInstance() {
        if (instance == null) {
            instance = new BookingManager();
        }
        return instance;
    }

    // Add a treatment to the system with a booking ID
    public void addTreatment(Treatment treatment) {
        treatments.put(treatment.getBookingId(), treatment);
        usedBookingIds.add(treatment.getBookingId());
    }

    // Cancel a treatment by booking ID and release the appointment
    public boolean cancelTreatment(String bookingId, Patient patient) {
        Treatment treatment = treatments.get(bookingId);
        if (treatment != null) {
            if (treatment.getStatus() == AppointmentStatus.BOOKED) {
                // Proceed with cancellation
                treatment.cancel(patient);
                CanceledTreatmentRecord record = new CanceledTreatmentRecord(
                        treatment.getName(),
                        treatment.getPhysiotherapist().getName(),
                        treatment.getPatient() != null ? treatment.getPatient().getName() : "Unknown Patient",
                        patient.getName(),
                        treatment.getDateTime()
                );
                canceledTreatments.add(record);
                System.out.println("Treatment canceled and made AVAILABLE again: " + treatment.getName());
                return true;
            } else {
                System.out.println("Treatment is already canceled or unavailable.");
            }
        }
        return false;
    }

    public boolean rebookTreatment(String bookingId, Patient patient) {
        if (patient == null) {
            System.out.println("Cannot rebook treatment. Patient is null.");
            return false;
        }

        Treatment treatment = treatments.get(bookingId);
        if (treatment != null && treatment.getStatus() == AppointmentStatus.CANCELLED) {
            treatment.setStatus(AppointmentStatus.BOOKED);  // Rebook as BOOKED
            canceledTreatments.remove(treatment);           // Remove from canceled list
            treatment.setPatient(patient);                  // Assign patient to the treatment
            return true;
        }
        return false;
    }

    public void cancelTreatmentForPatient(String bookingId, String patientId) {
        BookingManager bookingManager = BookingManager.getInstance();
        Patient patient = bookingManager.findPatientById(patientId);

        if (patient != null) {
            boolean success = bookingManager.cancelTreatment(bookingId, patient);

            if (success) {
                System.out.println("Treatment canceled successfully.");
            } else {
                System.out.println("Failed to cancel treatment. It might be already canceled or unavailable.");
            }
        } else {
            System.out.println("Patient not found.");
        }
    }

    // Add a patient to the system
    public void addPatient(Patient patient) {
        if (!patients.containsKey(patient.getId())) {
            patients.put(patient.getId(), patient);
            System.out.println("Patient added successfully.");
        } else {
            System.out.println("Patient with this ID already exists.");
        }
    }

    // Add a physiotherapist to the system
    public void addPhysiotherapist(Physiotherapist pt) {
        physiotherapists.put(pt.getId(), pt);
    }

    // Get all patients in the system
    public Collection<Patient> getAllPatients() {
        return patients.values();
    }

    // Get all physiotherapists in the system
    public Collection<Physiotherapist> getAllPhysiotherapists() {
        return physiotherapists.values();
    }

    // Find a patient by ID
    public Patient findPatientById(String id) {
        return patients.get(id);
    }

    // Find a physiotherapist by name
    public Physiotherapist findPhysiotherapistByName(String name) {
        for (Physiotherapist pt : physiotherapists.values()) {
            if (pt.getName().equalsIgnoreCase(name)) {
                return pt;
            }
        }
        return null;
    }

    // Getter for canceled treatments
   // public List<Treatment> getCanceledTreatments() {
   //     return canceledTreatments;
    //}
    public List<CanceledTreatmentRecord> getCanceledTreatments() {
        return canceledTreatments;
    }
    // Print final report
    public void printFinalReport() {
        System.out.println("--- Final Report ---");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Physiotherapist physio : physiotherapists.values()) {
            System.out.println("Physiotherapist: " + physio.getName());

            // Loop through all treatments, including canceled ones
            for (Treatment treatment : physio.getTreatments()) {
                String patientName = treatment.getPatient() != null ? treatment.getPatient().getName() : "N/A";
                String time = treatment.getDateTime().format(formatter);

                switch (treatment.getStatus()) {
                    case BOOKED:
                        System.out.println("  → " + treatment.getName()
                                + " | Patient: " + patientName
                                + " | Time: " + time
                                + " | Status: BOOKED");
                        break;

                    case CANCELLED:
                        String canceledBy = treatment.getCanceledBy() != null ? treatment.getCanceledBy() : "N/A";
                        System.out.println("  → " + treatment.getName()
                                + " | Patient: " + patientName
                                + " | Canceled By: " + canceledBy
                                + " | Time: " + time
                                + " | Status: CANCELLED");
                        break;

                    case AVAILABLE:
                        if (canceledTreatments.contains(treatment)) {
                            System.out.println("  → " + treatment.getName()
                                    + " | Physio: " + treatment.getPhysiotherapist().getName()
                                    + " | Canceled By: " + treatment.getCanceledBy()
                                    + " | Time: " + time
                                    + " | Status: CANCELED (Now Available)");
                        } else {
                            System.out.println("  → " + treatment.getName()
                                    + " | Patient: N/A"
                                    + " | Time: " + time
                                    + " | Status: AVAILABLE");
                        }
                        break;
                }
            }

        }
/*
        System.out.println("\n--- Canceled Treatments ---");
        for (Treatment treatment : canceledTreatments) {
            if (treatment.getStatus() == AppointmentStatus.CANCELLED) {
                String canceledPatient = treatment.getCanceledBy() != null ? treatment.getCanceledBy() : "N/A";

                System.out.println("  → " + treatment.getName()
                        + " | Physio: " + treatment.getPhysiotherapist().getName()
                        + " | Canceled By: " + canceledPatient
                        + " | Time: " + treatment.getDateTime().format(formatter)
                        + " | Status: CANCELED");
            }
        }

 */
        System.out.println("\n--- Canceled Treatments ---");

        for (CanceledTreatmentRecord record : canceledTreatments) {
            System.out.println("  → " + record.getTreatmentName()
                    + " | Physio: " + record.getPhysiotherapistName()
                    + " | Patient: " + record.getPatientName()
                    + " | Canceled By: " + record.getCanceledBy()
                    + " | Time: " + record.getTime().format(formatter));
        }
    }

    // Remove a patient from the system
    public void removePatient(String patientId) {
        if (patients.remove(patientId) != null) {
            System.out.println("Patient removed successfully.");
        } else {
            System.out.println("No patient found with that ID.");
        }
    }

    // Check if a booking ID is already used
    public boolean isBookingIdUsed(String bookingId) {
        return usedBookingIds.contains(bookingId);
    }
}

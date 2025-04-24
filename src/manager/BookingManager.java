package manager;

import model.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookingManager {
    private static BookingManager instance; // Singleton pattern
    private Map<String, Patient> patients;
    private Map<String, Physiotherapist> physiotherapists;
    private Map<String, Treatment> treatments; // Maps booking IDs to treatments
    private Set<String> usedBookingIds;
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

    public void addTreatment(Treatment treatment) {
        treatments.put(treatment.getBookingId(), treatment);
        usedBookingIds.add(treatment.getBookingId());
    }

    public boolean cancelTreatment(String bookingId, Patient patient) {
        Treatment treatment = treatments.get(bookingId);
        if (treatment != null && treatment.getStatus() == AppointmentStatus.BOOKED) {
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
        return false;
    }

    public boolean rebookTreatment(String bookingId, Patient patient) {
        if (patient == null) {
            System.out.println("Cannot rebook treatment. Patient is null.");
            return false;
        }

        Treatment treatment = treatments.get(bookingId);
        if (treatment != null && treatment.getStatus() == AppointmentStatus.AVAILABLE) {
            treatment.setStatus(AppointmentStatus.BOOKED);
            treatment.setPatient(patient);

            // Remove corresponding canceled treatment record
            canceledTreatments.removeIf(record -> record.getTreatmentName().equals(treatment.getName())
                    && record.getTime().equals(treatment.getDateTime()));

            System.out.println("Treatment rebooked successfully: " + treatment.getName());
            return true;
        }

        System.out.println("Treatment is not available for rebooking.");
        return false;
    }

    public void cancelTreatmentForPatient(String bookingId, String patientId) {
        Patient patient = findPatientById(patientId);
        if (patient != null) {
            boolean success = cancelTreatment(bookingId, patient);
            System.out.println(success ? "Treatment canceled successfully." :
                    "Failed to cancel treatment. It might be already canceled or unavailable.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    public void addPatient(Patient patient) {
        if (!patients.containsKey(patient.getId())) {
            patients.put(patient.getId(), patient);
            System.out.println("Patient added successfully.");
        } else {
            System.out.println("Patient with this ID already exists.");
        }
    }

    public void addPhysiotherapist(Physiotherapist pt) {
        physiotherapists.put(pt.getId(), pt);
    }

    public Collection<Patient> getAllPatients() {
        return patients.values();
    }

    public Collection<Physiotherapist> getAllPhysiotherapists() {
        return physiotherapists.values();
    }

    public Patient findPatientById(String id) {
        return patients.get(id);
    }

    public Physiotherapist findPhysiotherapistByName(String name) {
        for (Physiotherapist pt : physiotherapists.values()) {
            if (pt.getName().equalsIgnoreCase(name)) {
                return pt;
            }
        }
        return null;
    }

    public List<CanceledTreatmentRecord> getCanceledTreatments() {
        return canceledTreatments;
    }

    public void printFinalReport() {
        System.out.println("--- Final Report ---");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<Physiotherapist> sortedPhysios = new ArrayList<>(physiotherapists.values());
        sortedPhysios.sort(Comparator.comparing(Physiotherapist::getName));

        for (Physiotherapist physio : sortedPhysios) {
            System.out.println("Physiotherapist: " + physio.getName());

            List<Treatment> sortedTreatments = new ArrayList<>(physio.getTreatments());
            sortedTreatments.sort(Comparator.comparing(Treatment::getDateTime));

            for (Treatment treatment : sortedTreatments) {
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
                        boolean wasCanceled = canceledTreatments.stream()
                                .anyMatch(r -> r.getTreatmentName().equals(treatment.getName())
                                        && r.getTime().equals(treatment.getDateTime()));
                        if (wasCanceled) {
                            String canceledByName = treatment.getCanceledBy() != null ? treatment.getCanceledBy() : "N/A";
                            System.out.println("  → " + treatment.getName()
                                    + " | Physio: " + treatment.getPhysiotherapist().getName()
                                    + " | Canceled By: " + canceledByName
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

        System.out.println("\n--- Canceled Treatments ---");
        for (CanceledTreatmentRecord record : canceledTreatments) {
            String patientName = record.getPatientName() != null ? record.getPatientName() : "N/A";
            String canceledBy = record.getCanceledBy() != null ? record.getCanceledBy() : "N/A";

            System.out.println("  → " + record.getTreatmentName()
                    + " | Physio: " + record.getPhysiotherapistName()
                    + " | Patient: " + patientName
                    + " | Canceled By: " + canceledBy
                    + " | Time: " + record.getTime().format(formatter));
        }
    }

    public void removePatient(String patientId) {
        if (patients.remove(patientId) != null) {
            System.out.println("Patient removed successfully.");
        } else {
            System.out.println("No patient found with that ID.");
        }
    }

    public boolean isBookingIdUsed(String bookingId) {
        return usedBookingIds.contains(bookingId);
    }
}

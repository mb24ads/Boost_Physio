package manager;

import model.AppointmentStatus;
import model.Patient;
import model.Physiotherapist;
import model.Treatment;

import java.util.*;

public class BookingManager {
    private static BookingManager instance; // Singleton pattern
    private Map<String, Patient> patients;
    private Map<String, Physiotherapist> physiotherapists;
    private List<Treatment> canceledTreatments = new ArrayList<>();

    private BookingManager() {
        patients = new HashMap<>();
        physiotherapists = new HashMap<>();
    }

    public static BookingManager getInstance() {
        if (instance == null) {
            instance = new BookingManager();
        }
        return instance;
    }

    // Cancel and track
    public void cancelTreatment(Treatment treatment) {
        treatment.cancel();
        canceledTreatments.add(treatment);
    }

    public void addPatient(Patient patient) {
        if (!patients.containsKey(patient.getId())) {
            patients.put(patient.getId(), patient);
            System.out.println("Patient added successfully.");
        } else {
            System.out.println("Patient with this ID already exists.");
        }
    }

    public void removePatient(String patientId) {
        if (patients.remove(patientId) != null) {
            System.out.println("Patient removed successfully.");
        } else {
            System.out.println("No patient found with that ID.");
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

    // Show only truly available treatments
    public List<Treatment> getAvailableTreatments(Physiotherapist pt) {
        List<Treatment> available = new ArrayList<>();
        for (Treatment t : pt.getTreatments()) {
            if (t.getStatus() == AppointmentStatus.AVAILABLE) {
                available.add(t);
            }
        }
        return available;
    }

    public List<Treatment> getCanceledTreatments() {
        return canceledTreatments;
    }
}

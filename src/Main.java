
import manager.BookingManager;
import model.Patient;
import model.Physiotherapist;
import model.Treatment;
import model.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Boost Physio Booking System!");

       // BookingManager manager = new BookingManager();
        BookingManager manager = BookingManager.getInstance();

        // --- Add sample patients ---
        manager.addPatient(new Patient("P001", "Alice Smith", "10 Baker Street", "07123456789"));
        manager.addPatient(new Patient("P002", "Bob Johnson", "22 King’s Road", "07234567890"));
        manager.addPatient(new Patient("P003", "Charlie Evans", "33 Queen’s Ave", "07345678901"));
        manager.addPatient(new Patient("P004", "Diana Moore", "44 Elm Street", "07456789012"));
        manager.addPatient(new Patient("P005", "Ethan Brown", "55 Oak Lane", "07567890123"));

        // --- Add sample physiotherapists ---
        List<String> exp1 = Arrays.asList("Physiotherapy", "Rehabilitation");
        List<String> exp2 = Arrays.asList("Osteopathy", "Massage");
        List<String> exp3 = Arrays.asList("Physiotherapy", "Pool rehabilitation");

        Physiotherapist helen = new Physiotherapist("PT001", "Helen Wright", "1 Clinic Road", "07000000001", exp1);
        Physiotherapist mark = new Physiotherapist("PT002", "Mark Lee", "2 Clinic Road", "07000000002", exp2);
        Physiotherapist sara = new Physiotherapist("PT003", "Sara Green", "3 Clinic Road", "07000000003", exp3);

        // --- Add sample treatments for January ---
        helen.addTreatment(new Treatment("Neural mobilisation", LocalDateTime.of(2025, 1, 3, 10, 0), helen));
        helen.addTreatment(new Treatment("Acupuncture", LocalDateTime.of(2025, 1, 10, 14, 0), helen));
        mark.addTreatment(new Treatment("Massage", LocalDateTime.of(2025, 1, 5, 11, 0), mark));
        mark.addTreatment(new Treatment("Mobilisation of spine", LocalDateTime.of(2025, 1, 15, 16, 0), mark));
        sara.addTreatment(new Treatment("Pool rehabilitation", LocalDateTime.of(2025, 1, 8, 9, 0), sara));
        sara.addTreatment(new Treatment("Physiotherapy", LocalDateTime.of(2025, 1, 22, 13, 0), sara));

        // --- Register physios with the system ---
        manager.addPhysiotherapist(helen);
        manager.addPhysiotherapist(mark);
        manager.addPhysiotherapist(sara);

        // --- Confirm it worked ---
        System.out.println("\n--- Registered Patients ---");
        for (Patient p : manager.getAllPatients()) {
            System.out.println(p);
        }

        System.out.println("\n--- Registered Physiotherapists and Treatments ---");
        for (Physiotherapist pt : manager.getAllPhysiotherapists()) {
            System.out.println(pt);
            for (Treatment t : pt.getTreatments()) {
                System.out.println("  → " + t);
            }
        }

        while (true) {
            System.out.println("\n--- Boost Physio Booking System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Remove Patient");
            System.out.println("3. View Patients");
            System.out.println("4. View Physiotherapists & Treatments");
            System.out.println("5. Book Treatment by Physiotherapist");
            System.out.println("6. Book Treatment by Expertise");
            System.out.println("7. Cancel Treatment");
            System.out.println("8. Attend Appointment");
            System.out.println("9. Print final report");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();
                    manager.addPatient(new Patient(id, name, address, phone));
                    break;
                case 2:
                    System.out.print("Enter patient ID to remove: ");
                    String pid = scanner.nextLine();
                    manager.removePatient(pid);
                    break;
                case 3:
                    System.out.println("\nRegistered Patients:");
                    for (Patient p : manager.getAllPatients()) {
                        System.out.println(p);
                    }
                    break;
                case 4:
                    System.out.println("\nPhysiotherapists and Treatments:");
                    for (Physiotherapist pt : manager.getAllPhysiotherapists()) {
                        System.out.println(pt);
                        for (Treatment t : pt.getTreatments()) {
                            System.out.println("  → " + t);
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter patient ID: ");
                    String patId = scanner.nextLine();
                    Patient patient = manager.findPatientById(patId);
                    if (patient == null) {
                        System.out.println("Patient not found.");
                        break;
                    }
                    System.out.print("Enter physiotherapist name: ");
                    String ptName = scanner.nextLine();
                    Physiotherapist pt = manager.findPhysiotherapistByName(ptName);
                    if (pt == null) {
                        System.out.println("Physiotherapist not found.");
                        break;
                    }
                    List<Treatment> available = new ArrayList<>();
                    for (Treatment t : pt.getTreatments()) {
                        if (t.getStatus() == AppointmentStatus.AVAILABLE) {
                            available.add(t);
                        }
                    }
                    if (available.isEmpty()) {
                        System.out.println("No available treatments.");
                        break;
                    }
                    for (int i = 0; i < available.size(); i++) {
                        System.out.println((i + 1) + ". " + available.get(i));
                    }

                    // This is where you add the check for booking availability
                    System.out.print("Select a treatment to book: ");
                    int sel = scanner.nextInt();
                    scanner.nextLine();  // consume newline
                    if (sel >= 1 && sel <= available.size()) {
                        Treatment selectedTreatment = available.get(sel - 1);
                        if (selectedTreatment.getStatus() == AppointmentStatus.AVAILABLE) {
                            selectedTreatment.book(patient);
                            System.out.println("Appointment booked successfully.");
                        } else {
                            System.out.println("This treatment is already booked.");
                        }
                    } else {
                        System.out.println("Invalid selection.");
                    }
                    break;
                case 6:
                    System.out.print("Enter patient's ID: ");
                    String patientIdExp = scanner.nextLine();
                    Patient patientExp = manager.findPatientById(patientIdExp);

                    if (patientExp == null) {
                        System.out.println("Patient not found.");
                        break;
                    }

                    System.out.print("Enter area of expertise: ");
                    String expertise = scanner.nextLine();

                    List<Treatment> availableTreatments = new ArrayList<>();

                    for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                        if (physio.getExpertise().stream().anyMatch(e -> e.equalsIgnoreCase(expertise))) {
                            for (Treatment t : physio.getTreatments()) {
                                if (t.getStatus() == AppointmentStatus.AVAILABLE) {
                                    availableTreatments.add(t);
                                }
                            }
                        }
                    }

                    if (availableTreatments.isEmpty()) {
                        System.out.println("No available treatments for that expertise.");
                        break;
                    }

                    System.out.println("\nAvailable treatments:");
                    for (int i = 0; i < availableTreatments.size(); i++) {
                        System.out.println((i + 1) + ". " + availableTreatments.get(i));
                    }

                    System.out.print("Select a treatment to book (number): ");
                    int treatmentIndex = Integer.parseInt(scanner.nextLine()) - 1;

                    if (treatmentIndex < 0 || treatmentIndex >= availableTreatments.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Treatment selected = availableTreatments.get(treatmentIndex);
                    selected.book(patientExp);
                    System.out.println("Treatment booked successfully.");
                    break;

                case 7:
                    System.out.print("Enter your patient ID: ");
                    String cancelId = scanner.nextLine();
                    Patient pUser = manager.findPatientById(cancelId);
                    if (pUser == null) {
                        System.out.println("Patient not found.");
                        break;
                    }

                    List<Treatment> bookedTreatments = new ArrayList<>();
                    for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                        for (Treatment t : physio.getTreatments()) {
                            if (t.getPatient() != null && t.getPatient().getId().equals(pUser.getId())) {
                                bookedTreatments.add(t);
                            }
                        }
                    }

                    if (bookedTreatments.isEmpty()) {
                        System.out.println("You have no booked appointments.");
                        break;
                    }

                    System.out.println("\nYour current bookings:");
                    for (int i = 0; i < bookedTreatments.size(); i++) {
                        System.out.println((i + 1) + ". " + bookedTreatments.get(i));
                    }

                    System.out.print("Select the booking to cancel or change: ");
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    if (index < 0 || index >= bookedTreatments.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Treatment oldTreatment = bookedTreatments.get(index);

                    System.out.print("Do you want to (C)ancel or (C)hange this booking? [c/change]: ");
                    String action = scanner.nextLine().trim().toLowerCase();

                    if (action.equals("c") || action.equals("cancel")) {
                        oldTreatment.cancel();
                        manager.getCanceledTreatments().add(oldTreatment); // Add to canceled list
                        System.out.println("Canceled treatment added: " + oldTreatment.getName()); // Debug log
                        System.out.println("Appointment cancelled successfully.");                    } else if (action.equals("change")) {
                        oldTreatment.cancel();
                        System.out.println("Select a new treatment by physiotherapist’s name.");
                        // Reuse logic from case 5 to book a new treatment
                        System.out.print("Enter physiotherapist name: ");
                        String newPtName = scanner.nextLine();
                        Physiotherapist newPt = manager.findPhysiotherapistByName(newPtName);
                        if (newPt == null) {
                            System.out.println("Physiotherapist not found.");
                            break;
                        }

                        List<Treatment> newOptions = new ArrayList<>();
                        for (Treatment t : newPt.getTreatments()) {
                            if (t.getStatus() == AppointmentStatus.AVAILABLE) {
                                newOptions.add(t);
                            }
                        }

                        if (newOptions.isEmpty()) {
                            System.out.println("No available treatments.");
                            break;
                        }

                        for (int i = 0; i < newOptions.size(); i++) {
                            System.out.println((i + 1) + ". " + newOptions.get(i));
                        }

                        System.out.print("Select a new treatment: ");
                        int newIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        if (newIndex >= 0 && newIndex < newOptions.size()) {
                            newOptions.get(newIndex).book(pUser);
                            System.out.println("Treatment changed successfully.");
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    } else {
                        System.out.println("Invalid action.");
                    }
                    break;

                case 8:
                    System.out.print("Enter your patient ID: ");
                    String attendId = scanner.nextLine();
                    Patient attendingPatient = manager.findPatientById(attendId);
                    if (attendingPatient == null) {
                        System.out.println("Patient not found.");
                        break;
                    }

                    List<Treatment> bookedList = new ArrayList<>();
                    for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                        for (Treatment t : physio.getTreatments()) {
                            if (t.getPatient() != null && t.getPatient().equals(attendingPatient)
                                    && t.getStatus() == AppointmentStatus.BOOKED) {
                                bookedList.add(t);
                            }
                        }
                    }

                    if (bookedList.isEmpty()) {
                        System.out.println("You have no booked appointments to attend.");
                        break;
                    }

                    System.out.println("\nYour booked appointments:");
                    for (int i = 0; i < bookedList.size(); i++) {
                        System.out.println((i + 1) + ". " + bookedList.get(i));
                    }

                    System.out.print("Select the appointment you are attending (number): ");
                    int attendIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (attendIndex < 0 || attendIndex >= bookedList.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    bookedList.get(attendIndex).attend();
                    System.out.println("Appointment marked as attended.");
                    break;

                case 9:
                    System.out.println("\n--- Final Report ---");

                    Map<Physiotherapist, Integer> attendanceCount = new HashMap<>();

                    for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                        System.out.println("\nPhysiotherapist: " + physio.getName());

                        for (Treatment t : physio.getTreatments()) {
                            // Display all treatments
                            String patientName = (t.getPatient() != null) ? t.getPatient().getName() : "N/A";
                            System.out.printf("  → %-30s | Patient: %-20s | Time: %-20s | Status: %s\n",
                                    t.getName(), patientName, t.getDateTime(), t.getStatus());

                            // Count only attended treatments for ranking
                            if (t.getStatus() == AppointmentStatus.ATTENDED) {
                                attendanceCount.put(physio, attendanceCount.getOrDefault(physio, 0) + 1);
                            }
                        }
                    }

                    // Sort and display physiotherapists by number of attended appointments
                    System.out.println("\n--- Ranking by Attended Appointments ---");
                    attendanceCount.entrySet().stream()
                            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                            .forEach(entry -> System.out.printf("%-20s → %d attended\n",
                                    entry.getKey().getName(), entry.getValue()));

                    // Display canceled treatments in a separate section
                    System.out.println("\n--- Canceled Treatments ---");
                    for (Treatment t : manager.getCanceledTreatments()) {
                        String physioName = t.getPhysiotherapist().getName();
                        String patientName = (t.getPatient() != null) ? t.getPatient().getName() : "N/A";
                        System.out.printf("  → %-30s | Physio: %-20s | Patient: %-20s | Time: %-20s | Status: %s\n",
                                t.getName(), physioName, patientName, t.getDateTime(), t.getStatus());
                    }
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");

            }
        }
    }
}
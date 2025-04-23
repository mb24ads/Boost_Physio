import manager.BookingManager;
import model.*;

import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;


import java.time.LocalDateTime;
import java.util.*;



public class Main {

    private static final String BOLD = "\033[1m";
    private static final String RESET = "\033[0m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🌟✨ WELCOME TO BOOST PHYSIO CLINIC BOOKING SYSTEM ✨🌟");
        System.out.println("🏥 Book, Cancel, or Manage Your Appointments with Ease! 💼🗓️");
        System.out.println("------------------------------------------------------------");

        BookingManager manager = BookingManager.getInstance();

        // Create a list of expertise for Danial Andrew
        List<String> exp1 = Arrays.asList("Physiotherapy", "Rehabilitation");
        // Create a new Physiotherapist for Danial Andrew
        Physiotherapist Danial = new Physiotherapist("DR7890", "Dr.Danial Andrew", "Barking", "07345567655", exp1);

        // Create a list of expertise for Robert Smith
        List<String> exp2 = Arrays.asList("Osteopathy", "Massage");
        // Create a new Physiotherapist for Robert Smith
        Physiotherapist Robert = new Physiotherapist("DR7891", "Dr.Robert Smith", "Ilford", "07212837388", exp2);

        // Create a list of expertise for Kim Yung
        List<String> exp3 = Arrays.asList("Physiotherapy", "Pool rehabilitation");
        // Create a new Physiotherapist for Kim Yung
        Physiotherapist Kim = new Physiotherapist("DR7892", "Dr.Kim Yung", "Kent", "07863453288", exp3);

        // --- Add treatments for each physiotherapist ---

        LocalDateTime sharedDateTime = LocalDateTime.of(2025, 1, 3, 10, 0);

        Danial.addTreatment(new Treatment("Neural mobilisation",sharedDateTime, Danial, generateBookingId()));
        Danial.addTreatment(new Treatment("Acupuncture", LocalDateTime.of(2025, 1, 10, 14, 0), Danial, generateBookingId()));
        Robert.addTreatment(new Treatment("Massage",sharedDateTime, Robert, generateBookingId()));
        Robert.addTreatment(new Treatment("Mobilisation of spine", LocalDateTime.of(2025, 1, 15, 16, 0), Robert, generateBookingId()));
        Kim.addTreatment(new Treatment("Pool rehabilitation", LocalDateTime.of(2025, 1, 8, 9, 0), Kim, generateBookingId()));
        Kim.addTreatment(new Treatment("Physiotherapy", LocalDateTime.of(2025, 1, 22, 13, 0), Kim, generateBookingId()));

        //  Register physiotherapists with the system
        manager.addPhysiotherapist(Danial);
        manager.addPhysiotherapist(Robert);
        manager.addPhysiotherapist(Kim);



        // Add sample patients
        manager.addPatient(new Patient("P0001", "David Wilson", "25 Glencoe Street", "07245689644"));
        manager.addPatient(new Patient("P0002", "Emily Yung", "67 WestBury Road", "07345678822"));
        manager.addPatient(new Patient("P0003", "Michael Brown", "78 Elizbeth’s Ave", "07875634566"));
        manager.addPatient(new Patient("P0004", "Jane Doe", "34 Commet Street", "07239856844"));
        manager.addPatient(new Patient("P0005", "John Smith", "76 Lakers Lane", "07998563212"));

        // Confirm it worked
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

            // Clean header with section lines for clarity
            System.out.println("────────────────────────────────────────────────────────────────────────");
            System.out.println("                   🌟 BOOST PHYSIO CLINIC 🌟                             ");
            System.out.println("               Our Mission 🏥 || Keep Healty 💪         ");
            System.out.println("────────────────────────────────────────────────────────────────────────");
            System.out.println("                           MAIN MENU 📋                                 ");
            System.out.println("────────────────────────────────────────────────────────────────────────");

// Using printf for aligned options with padding and consistent length
            System.out.printf("  (1)   🆕 %-30s \n", "New Entry");
            System.out.printf("  (2)   🗑️ %-30s \n", "Delete Record");
            System.out.printf("  (3)   📊 %-30s \n", "Overview");
            System.out.printf("  (4)   ℹ️ %-30s ️\n", "Services Info");
            System.out.printf("  (5)   📅 %-30s \n", "Direct Booking");
            System.out.printf("  (6)   🤖 %-30s \n", "Match Needs");
            System.out.printf("  (7)   🔧 %-30s ️\n", "Manage Booking");
            System.out.printf("  (8)   ✅ %-30s \n", "Mark Attended");
            System.out.printf("  (9)   📑 %-30s \n", "Summary Report");
            System.out.printf("  (0)   ❌ %-30s \n", "Close Program");

// Section separator for improved clarity
          //  System.out.println("────────────────────────────────────────────────────────────────────────");
            System.out.println("🔹 Please select an option by entering the corresponding number: 🔹");
          //  System.out.println("────────────────────────────────────────────────────────────────────────");

// Spacing for user input visibility
          //  System.out.println("\n────────────────────────────────────────────────────────────────────────");
            System.out.print("Enter your option: ");
           // System.out.println("────────────────────────────────────────────────────────────────────────");


// Optional: This can be followed by a logic to handle user input and selections.

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter Mobile Number: ");
                    String phone = scanner.nextLine();
                    manager.addPatient(new Patient(id, name, address, phone));
                    break;
                case 2:
                    System.out.print("Enter Patient ID to remove: ");
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
                    System.out.print("Enter Patient ID: ");
                    String patId = scanner.nextLine();
                    Patient patient = manager.findPatientById(patId);
                    if (patient == null) {
                        System.out.println("Patient not found.");
                        break;
                    }
                    System.out.print("Enter physiotherapist Name: ");
                    String ptName = scanner.nextLine();
                    Physiotherapist pt = manager.findPhysiotherapistByName(ptName);
                    if (pt == null) {
                        System.out.println("Physiotherapist not found !");
                        break;
                    }
                    List<Treatment> available = new ArrayList<>();
                    for (Treatment t : pt.getTreatments()) {
                        if (t.getStatus() == AppointmentStatus.AVAILABLE) {
                            available.add(t);
                        }
                    }
                    if (available.isEmpty()) {
                        System.out.println("No Available Treatments !");
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
                        boolean hasConflict = false;
                        for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                            for (Treatment booked : physio.getTreatments()) {
                                if (booked.getPatient() != null && booked.getPatient().equals(patient)) {
                                    if (booked.getDateTime().equals(selectedTreatment.getDateTime())) {
                                        hasConflict = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (hasConflict) {
                            System.out.println("Booking conflict: You already have an appointment at this time.");
                        } else {
                            selectedTreatment.book(patient);
                            System.out.println("Appointment Booked Successfully.");
                        }
                    } else {
                        System.out.println("Invalid selection.");
                    }
                    break;

                case 6:
                    System.out.print("Enter Patient's ID: ");
                    String patientIdExp = scanner.nextLine();
                    Patient patientExp = manager.findPatientById(patientIdExp);

                    if (patientExp == null) {
                        System.out.println("Patient not found !");
                        break;
                    }

                    System.out.print("Enter Area of Expertise: ");
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
                        System.out.println("No Available Treatment for that expertise.");
                        break;
                    }

                    System.out.println("\nAvailable Treatments:");
                    for (int i = 0; i < availableTreatments.size(); i++) {
                        System.out.println((i + 1) + ". " + availableTreatments.get(i));
                    }

                    System.out.print("Select a Treatment to book (number): ");
                    int treatmentIndex = Integer.parseInt(scanner.nextLine()) - 1;

                    if (treatmentIndex < 0 || treatmentIndex >= availableTreatments.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Treatment selected = availableTreatments.get(treatmentIndex);
                    boolean conflict = false;
                    for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                        for (Treatment t : physio.getTreatments()) {
                            if (t.getPatient() != null && t.getPatient().equals(patientExp)) {
                                if (t.getDateTime().equals(selected.getDateTime())) {
                                    conflict = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (conflict) {
                        System.out.println("Booking Conflict: You already have an appointment at this time.");
                    } else {
                        selected.book(patientExp);
                        System.out.println("Treatment Booked Successfully.");
                    }
                    break;

                case 7:
                    System.out.print("Enter your Patient's ID: ");
                    String cancelId = scanner.nextLine();
                    Patient pUser = manager.findPatientById(cancelId);
                    if (pUser == null) {
                        System.out.println("Patient not found!");
                        break;
                    }

                    // Get booked treatments for the patient
                    List<Treatment> bookedTreatments = new ArrayList<>();
                    for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                        for (Treatment t : physio.getTreatments()) {
                            if (t.getPatient() != null && t.getPatient().getId().equals(pUser.getId())) {
                                bookedTreatments.add(t);
                            }
                        }
                    }

                    if (bookedTreatments.isEmpty()) {
                        System.out.println("You have no booked appointments!");
                        break;
                    }

                    System.out.println("\nYour Current Bookings:");
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
                        // Ensure patient is not null before calling getName()
                        if (oldTreatment.getPatient() != null) {
                            String patientName = oldTreatment.getPatient().getName();  // Store name before canceling
                            oldTreatment.cancel(pUser);  // Pass the patient who is canceling the treatment
                            oldTreatment.setStatus(AppointmentStatus.AVAILABLE);  // Release the appointment slot

                            // Record the canceled treatment for history tracking
                            CanceledTreatmentRecord canceled = new CanceledTreatmentRecord(
                                    oldTreatment.getName(),
                                    oldTreatment.getPhysiotherapist().getName(),
                                    patientName,  // Use stored patient name
                                    pUser.getName(),
                                    oldTreatment.getDateTime()
                            );
                            manager.getCanceledTreatments().add(canceled);
                            System.out.println("Booking cancelled for " + patientName);  // Custom success message
                        } else {
                            System.out.println("Unable to cancel the treatment: No patient associated!");
                        }
                    }

                    else if (action.equals("change")) {
                        // Capture patient name before canceling
                        String patientName = oldTreatment.getPatient() != null ? oldTreatment.getPatient().getName() : "N/A";

                    // Cancel the old appointment
                        oldTreatment.cancel(pUser);
                        oldTreatment.setStatus(AppointmentStatus.AVAILABLE);

                    // Record the canceled treatment for history tracking
                        CanceledTreatmentRecord canceled = new CanceledTreatmentRecord(
                                oldTreatment.getName(),
                                oldTreatment.getPhysiotherapist().getName(),
                                patientName,
                                pUser.getName(),
                                oldTreatment.getDateTime()
                        );
                        manager.getCanceledTreatments().add(canceled);

                        System.out.println("Appointment cancelled. Please choose a new appointment to book.");

                        // Rebooking flow (same as case 5 or 6 - simplified version here)
                        List<Treatment> allAvailable = new ArrayList<>();
                        for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                            for (Treatment t : physio.getTreatments()) {
                                if (t.getStatus() == AppointmentStatus.AVAILABLE) {
                                    allAvailable.add(t);
                                }
                            }
                        }

                        if (allAvailable.isEmpty()) {
                            System.out.println("No available appointments at this time!");
                            break;
                        }

                        System.out.println("\nAvailable Appointments:");
                        for (int i = 0; i < allAvailable.size(); i++) {
                            System.out.println((i + 1) + ". " + allAvailable.get(i));
                        }

                        System.out.print("Select a new appointment (Number): ");
                        int newIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        if (newIndex < 0 || newIndex >= allAvailable.size()) {
                            System.out.println("Invalid selection.");
                            break;
                        }

                        Treatment newTreatment = allAvailable.get(newIndex);

                        // Check for conflicts
                        boolean conflicts = false;
                        for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                            for (Treatment t : physio.getTreatments()) {
                                if (t.getPatient() != null && t.getPatient().equals(pUser)
                                        && t.getDateTime().equals(newTreatment.getDateTime())) {
                                    conflicts = true;
                                    break;
                                }
                            }
                        }

                        if (conflicts) {
                            System.out.println("Booking conflict: You already have an appointment at this time.");
                        } else {
                            newTreatment.book(pUser);
                            System.out.println("Appointment successfully rebooked.");
                        }
                    }

                    break;


                case 8:
                    System.out.print("Enter your Patient's ID: ");
                    String attendId = scanner.nextLine();
                    Patient attendingPatient = manager.findPatientById(attendId);
                    if (attendingPatient == null) {
                        System.out.println("Patient Not Found !");
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
                        System.out.println("You have no Booked Appointments to Attend !");
                        break;
                    }

                    System.out.println("\nYour Booked Appointments:");
                    for (int i = 0; i < bookedList.size(); i++) {
                        System.out.println((i + 1) + ". " + bookedList.get(i));
                    }

                    System.out.print("Select the Appointment you are attending (number): ");
                    int attendIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (attendIndex < 0 || attendIndex >= bookedList.size()) {
                        System.out.println("Invalid selection !");
                        break;
                    }

                    bookedList.get(attendIndex).attend();
                    System.out.println("Appointment Roberted as Attended.");
                    break;

                case 9:
                    System.out.println("\n--- Final Report ---");

                    // Map to track the number of attended treatments per physiotherapist
                    Map<Physiotherapist, Integer> attendanceCount = new HashMap<>();

                    // Loop through all physiotherapists
                    for (Physiotherapist physio : manager.getAllPhysiotherapists()) {
                        System.out.println("\nPhysiotherapist: " + physio.getName());

                        // Loop through all treatments of the physiotherapist
                        for (Treatment t : physio.getTreatments()) {
                            // Skip canceled treatments in this main section
                            AppointmentStatus status = t.getStatus();
                            String statusIcon = switch (status) {
                                case ATTENDED -> "🎯";
                                case CANCELLED -> "❌";
                                case AVAILABLE -> "🟢";
                                case BOOKED -> "⭕";
                            };

                            String patientName = (t.getPatient() != null) ? t.getPatient().getName() : "N/A";
                            System.out.printf("  → %-30s | Patient: %-20s | Time: %-20s | Status: %s %s\n",
                                    t.getName(), patientName, formatDateTimeRange(t.getDateTime()), statusIcon, status);

                            // Count attended treatments for ranking
                            if (status == AppointmentStatus.ATTENDED) {
                                attendanceCount.put(physio, attendanceCount.getOrDefault(physio, 0) + 1);
                            }
                        }
                    }

                    // Sort and display physiotherapists by number of attended treatments
                    System.out.println("\n--- Ranking by Attended Appointments ---");
                    attendanceCount.entrySet().stream()
                            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                            .forEach(entry -> System.out.printf("%-20s → %d attended\n", entry.getKey().getName(), entry.getValue()));

                    // Canceled treatments section
                    System.out.println("\n--- Cancelled Treatments ---");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                    for (CanceledTreatmentRecord record : manager.getCanceledTreatments()) {
                        System.out.printf("  → %-30s | Physio: %-20s |  Cancelled By: %-20s | Status: CANCELLED ❌\n",
                                record.getTreatmentName(),
                                record.getPhysiotherapistName(),
                                record.getPatientName(),
                                record.getCanceledBy(),
                                record.getTime().format(formatter)
                        );
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

    private static String formatDateTimeRange(LocalDateTime start) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE");
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String dayOfWeek = start.format(dayFormatter);
        int dayOfMonth = start.getDayOfMonth();
        String dayWithSuffix = getDayWithSuffix(dayOfMonth);
        String monthYear = start.format(monthYearFormatter);
        String startTime = start.format(timeFormatter);
        String endTime = start.plusHours(1).format(timeFormatter); // assuming 1-hour treatment

        return String.format("%s %s %s, %s-%s", dayOfWeek, dayWithSuffix, monthYear, startTime, endTime);
    }

    // Adds ordinal suffix to the day
    private static String getDayWithSuffix(int day) {
        if (day >= 11 && day <= 13) return day + "th";
        return switch (day % 10) {
            case 1 -> day + "st";
            case 2 -> day + "nd";
            case 3 -> day + "rd";
            default -> day + "th";
        };
    }


    // Method to generate unique booking ID
    public static String generateBookingId() {
        return UUID.randomUUID().toString().substring(0,4);  // This generates a unique ID based on UUID
    }
}
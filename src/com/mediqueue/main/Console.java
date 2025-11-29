package com.mediqueue.main;

import com.mediqueue.model.*;
import com.mediqueue.service.QueueManager;

import java.util.Optional;
import java.util.Scanner;

public class Console {

    private final QueueManager queueManager = new QueueManager();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;

        while (running) {
            displayHeader();
            displayMenu();

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addPatient();
                case "2" -> queueManager.displayQueue();
                case "3" -> updatePatient();
                case "4" -> removePatient();
                case "5" -> searchPatient();
                case "6" -> {
                    running = false;
                    System.out.println("\nExiting MediQueue. Goodbye!");
                }
                default -> System.out.println("\nInvalid option. Enter 1-6.");
            }
        }
    }

    private void displayHeader() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║       MediQueue System         ║");
        System.out.println("╚════════════════════════════════╝");
    }

    private void displayMenu() {
        System.out.println("""
                1. Add Patient
                2. Display Queue
                3. Update Patient
                4. Remove Patient
                5. Search Patient
                6. Exit""");
        System.out.print("\nChoose an option: ");
    }

    private void addPatient() {
        System.out.println("\nSelect Patient Type:");
        System.out.println("1. Emergency");
        System.out.println("2. Scheduled");
        System.out.println("3. Regular");

        int typeChoice = getValidIntInput("Choose (1-3): ", 1, 3);
        String type = switch (typeChoice) {
            case 1 -> "Emergency";
            case 2 -> "Scheduled";
            default -> "Regular";
        };

        String name = getNonEmptyString("Enter name: ");
        int age = getValidAge("Enter age: "); 
        
        String condition = getNonEmptyString("Enter condition: ");

        Patient newPatient;
        switch (type) {
            case "Emergency" -> newPatient = new EmergencyPatient(name, age, condition);
            case "Regular" -> newPatient = new RegularPatient(name, age, condition);
            default -> {
                int priority = getValidIntInput("Enter priority (lower = higher priority): ", 1, Integer.MAX_VALUE);
                newPatient = new ScheduledPatient(name, age, condition, priority);
            }
        }

        queueManager.addPatient(newPatient);
        System.out.println("\n✅ " + newPatient.getName() + " added to the queue!");
    }

    private void updatePatient() {
        String name = getNonEmptyString("Enter patient name to update: ");
        String newCondition = getNonEmptyString("Enter new condition: ");
        int newPriority = getValidIntInput("Enter new priority (lower = higher priority): ", 1, Integer.MAX_VALUE);

        queueManager.updatePatient(name, newCondition, newPriority);
    }

    private void removePatient() {
        String name = getNonEmptyString("Enter patient name to remove: ");
        queueManager.removePatient(name);
    }

    private void searchPatient() {
        String name = getNonEmptyString("Enter patient name to search: ");
        Optional<Patient> patientOpt = queueManager.findPatientByName(name);

        if (patientOpt.isPresent()) {
            System.out.println("\nPatient Found:");
            patientOpt.get().displayInfo();
        } else {
            System.out.println("\n❌ " + name + " not found in the queue.");
        }
    }

    // Helper methods
    private String getNonEmptyString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty() && !input.matches(".*\\d.*")) break;
            System.out.println("Invalid input. Please try again.");
        }
        return input;
    }

    private int getValidIntInput(String prompt, int min, int max) {
        int number;
        while (true) {
            try {
                System.out.print(prompt);
                number = Integer.parseInt(scanner.nextLine().trim());
                if (number < min || number > max) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number between " + min + " and " + max + ".");
            }
        }
        return number;
    }

    private int getValidAge(String prompt) {  // <-- new method for age
        int age;
        while (true) {
            try {
                System.out.print(prompt);
                age = Integer.parseInt(scanner.nextLine().trim());
                if (age < 1 || age > 150) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Enter valid age");
            }
        }
        return age;
    }
}

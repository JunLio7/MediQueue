package com.mediqueue.main;

import com.mediqueue.model.*;
import com.mediqueue.service.QueueManager;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        QueueManager queueManager = new QueueManager();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            
            while (running) {
                System.out.println("\n--- MediQueue Management System ---");
                System.out.println("1. Add Patient");
                System.out.println("2. Display Queue");
                System.out.println("3. Update Patient");
                System.out.println("4. Remove Patient");
                System.out.println("5. Search Patient");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                
                String choice = scanner.nextLine();
                
                switch (choice) {
                    case "1" -> {
                        // --- Select patient type ---
                        System.out.println("Enter patient type:");
                        System.out.println("1. Emergency");
                        System.out.println("2. Scheduled");
                        System.out.println("3. Regular");
                        
                        int typeChoice = 0;
                        while (true) {
                            System.out.print("Choose (1-3): ");
                            try {
                                typeChoice = Integer.parseInt(scanner.nextLine());
                                if (typeChoice < 1 || typeChoice > 3) {
                                    System.out.println("Invalid choice. Enter 1, 2, or 3.");
                                } else break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Enter a number 1-3.");
                            }
                        }
                        
                        String type = switch (typeChoice) {
                            case 1 -> "Emergency";
                            case 2 -> "Scheduled";
                            default -> "Regular";
                        };
                        
                        // --- Name validation ---
                        String name;
                        while (true) {
                            System.out.print("Enter name: ");
                            name = scanner.nextLine().trim();
                            if (name.isEmpty()) {
                                System.out.println("Name cannot be blank.");
                            } else if (name.matches(".*\\d.*")) {
                                System.out.println("Name cannot contain numbers.");
                            } else break;
                        }
                        
                        // --- Age validation ---
                        int age = 0;
                        while (true) {
                            System.out.print("Enter age: ");
                            try {
                                age = Integer.parseInt(scanner.nextLine());
                                if (age <= 0) System.out.println("Age must be a positive number.");
                                else break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Enter a valid number for age.");
                            }
                        }
                        
                        // --- Condition validation ---
                        String condition;
                        while (true) {
                            System.out.print("Enter condition: ");
                            condition = scanner.nextLine().trim();
                            if (condition.isEmpty()) {
                                System.out.println("Condition cannot be blank.");
                            } else break;
                        }
                        
                        // --- Create Patient object ---
                        Patient newPatient;
                        switch (type) {
                            case "Emergency" -> newPatient = new EmergencyPatient(name, age, condition);
                            case "Regular" -> newPatient = new RegularPatient(name, age, condition);
                            default -> { // Scheduled
                                int priority = 0;
                                while (true) {
                                    System.out.print("Enter priority (lower = higher priority): ");
                                    try {
                                        priority = Integer.parseInt(scanner.nextLine());
                                        if (priority < 1) System.out.println("Priority must be a positive number.");
                                        else break;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Enter a valid number.");
                                    }
                                }
                                newPatient = new ScheduledPatient(name, age, condition, priority);
                            }
                        }
                        
                        queueManager.addPatient(newPatient);
                        System.out.println(newPatient.getName() + " added to the queue.");
                    }
                    
                    case "2" -> queueManager.displayQueue();
                    
                    case "3" -> { // Update patient
                        System.out.print("Enter patient name to update: ");
                        String updateName = scanner.nextLine().trim();
                        if (updateName.isEmpty()) {
                            System.out.println("Name cannot be blank.");
                            break;
                        }
                        
                        System.out.print("Enter new condition: ");
                        String newCondition = scanner.nextLine().trim();
                        if (newCondition.isEmpty()) {
                            System.out.println("Condition cannot be blank.");
                            break;
                        }
                        
                        int newPriority = 0;
                        while (true) {
                            System.out.print("Enter new priority (lower = higher priority): ");
                            try {
                                newPriority = Integer.parseInt(scanner.nextLine());
                                if (newPriority < 1) System.out.println("Priority must be a positive number.");
                                else break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Enter a valid number.");
                            }
                        }
                        
                        queueManager.updatePatient(updateName, newCondition, newPriority);
                    }
                    
                    case "4" -> { // Remove patient
                        System.out.print("Enter patient name to remove: ");
                        String removeName = scanner.nextLine().trim();
                        if (removeName.isEmpty()) {
                            System.out.println("Name cannot be blank.");
                            break;
                        }
                        queueManager.removePatient(removeName);
                    }
                    
                    case "5" -> { // Search patient
                        System.out.print("Enter patient name to search: ");
                        String searchName = scanner.nextLine().trim();
                        if (searchName.isEmpty()) {
                            System.out.println("Name cannot be blank.");
                            break;
                        }
                        Optional<Patient> patientOpt = queueManager.findPatientByName(searchName);
                        if (patientOpt.isPresent()) {
                            System.out.println("Patient found:");
                            patientOpt.get().displayInfo();
                        } else {
                            System.out.println(searchName + " not found in the queue.");
                        }
                    }
                    
                    case "6" -> { // Exit
                        running = false;
                        System.out.println("Exiting MediQueue. Goodbye!");
                    }

                    default -> System.out.println("Invalid option. Enter 1-6.");
                }
            }
        }
    }
}

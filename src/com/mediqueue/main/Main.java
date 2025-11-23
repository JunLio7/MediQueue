package com.mediqueue.main;

import com.mediqueue.model.*;
import com.mediqueue.service.QueueManager;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    /**
     * @param args
     */
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
                                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }

                        String type = switch (typeChoice) {
                            case 1 -> "Emergency";
                            case 2 -> "Scheduled";
                            case 3 -> "Regular";
                            default -> "Regular";
                        };

                        // NAME VALIDATION
                        String name;
                        while (true) {
                            System.out.print("Enter name: ");
                            name = scanner.nextLine().trim();
                            if (name.isEmpty() || name.matches(".*\\d.*")) {
                                System.out.println("Invalid name. Name should not be empty or contain numbers.");
                            } else {
                                break;
                            }
                        }

                        // AGE VALIDATION
                        int age = 0;
                        while (true) {
                            System.out.print("Enter age: ");
                            try {
                                age = Integer.parseInt(scanner.nextLine());
                                if (age <= 0) {
                                    System.out.println("Age must be a positive number.");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number for age.");
                            }
                        }

                        System.out.print("Enter condition: ");
                        String condition = scanner.nextLine().trim();

                        Patient newPatient;

                    switch (type) {
                        case "Regular" -> newPatient = new RegularPatient(name, age, condition);
                        case "Emergency" -> newPatient = new EmergencyPatient(name, age, condition);
                        default -> {
                            // Scheduled
                            int priority = 0;
                            while (true) {
                                System.out.print("Enter priority (integer, lower is higher priority): ");
                                try {
                                    priority = Integer.parseInt(scanner.nextLine());
                                    if (priority < 1) {
                                        System.out.println("Priority must be a positive integer.");
                                    } else {
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a number for priority.");
                                }
                            }
                            newPatient = new ScheduledPatient(name, age, condition, priority);
                        }
                    }

                        queueManager.addPatient(newPatient);
                        System.out.println(newPatient.getName() + " added to the queue.");
                    }

                    case "2" -> queueManager.displayQueue();

                    case "3" -> {
                        System.out.print("Enter patient name to update: ");
                        String updateName = scanner.nextLine().trim();

                        System.out.print("Enter new condition: ");
                        String newCondition = scanner.nextLine().trim();

                        int newPriority = 0;
                        while (true) {
                            System.out.print("Enter new priority (integer, lower is higher priority): ");
                            try {
                                newPriority = Integer.parseInt(scanner.nextLine());
                                if (newPriority < 1) {
                                    System.out.println("Priority must be a positive integer.");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number for priority.");
                            }
                        }

                        queueManager.updatePatient(updateName, newCondition, newPriority);
                    }

                    case "4" -> {
                        System.out.print("Enter patient name to remove: ");
                        String removeName = scanner.nextLine().trim();
                        queueManager.removePatient(removeName);
                    }

                    case "5" -> {
                        System.out.print("Enter patient name to search: ");
                        String searchName = scanner.nextLine().trim();
                        Optional<Patient> patientOpt = queueManager.findPatientByName(searchName);
                        if (patientOpt.isPresent()) {
                            System.out.println("Patient found:");
                            System.out.println(patientOpt.get());
                        } else {
                            System.out.println(searchName + " not found in the queue.");
                        }
                    }

                    case "6" -> {
                        running = false;
                        System.out.println("Exiting MediQueue. Goodbye!");
                    }

                    default -> System.out.println("Invalid option. Please enter a number between 1 and 6.");
                }
            }
        }
    }
}

package com.mediqueue.service;

import com.mediqueue.model.Patient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueueManager {
    private final List<Patient> queue;

    public QueueManager() {
        queue = new ArrayList<>();
    }

    // CREATE
    public void addPatient(Patient p) {
        queue.add(p);
        sortByPriority();
    }

    // READ
    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.println("Patient Queue:");
        for (Patient p : queue) {
            System.out.println(p);
        }
    }

    public Optional<Patient> findPatientByName(String name) {
        return queue.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
    }

    // UPDATE
    public void updatePatient(String name, String newCondition, int newPriority) {
        Optional<Patient> patientOpt = findPatientByName(name);
        if (patientOpt.isPresent()) {
            Patient p = patientOpt.get();
            p.setCondition(newCondition);
            p.setPriority(newPriority);
            sortByPriority();
            System.out.println(name + " has been updated.");
        } else {
            System.out.println(name + " not found.");
        }
    }

    // DELETE
    public void removePatient(String name) {
        Optional<Patient> patientOpt = findPatientByName(name);
        if (patientOpt.isPresent()) {
            queue.remove(patientOpt.get());
            System.out.println(name + " has been removed from the queue.");
        } else {
            System.out.println(name + " not found.");
        }
    }

    // SORT
    private void sortByPriority() {
        queue.sort((p1, p2) -> Integer.compare(p1.getPriority(), p2.getPriority()));
    }

    public List<Patient> getAllPatients() {
        return queue;
    }
}

package com.mediqueue.model;

public class EmergencyPatient extends Patient {
    public EmergencyPatient(String name, int age, String condition) {
        super(name, age, condition, 1); // priority 1
    }

    @Override
    public String getType() {
        return "Emergency Patient";
    }

    @Override
    public void displayInfo() {
        super.displayInfo(); // prints base info once
    }
}

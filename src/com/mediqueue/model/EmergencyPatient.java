package com.mediqueue.model;

public class EmergencyPatient extends Patient {
    public EmergencyPatient(String name, int age, String condition) {
        super(name, age, condition, 1); // highest priority
    }

    @Override
    public String getType() { return "Emergency Patient"; }
}

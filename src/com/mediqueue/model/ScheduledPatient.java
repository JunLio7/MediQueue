package com.mediqueue.model;

public class ScheduledPatient extends Patient {
    public ScheduledPatient(String name, int age, String condition, int priority) {
        super(name, age, condition, priority);
    }

    @Override
    public String getType() { return "Scheduled Patient"; }
}

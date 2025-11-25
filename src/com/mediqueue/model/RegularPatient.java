package com.mediqueue.model;

public class RegularPatient extends Patient {
    public RegularPatient(String name, int age, String condition) {
        super(name, age, condition, 3);
    }

    @Override
    public String getType() {
        return "Regular Patient";
    }

}

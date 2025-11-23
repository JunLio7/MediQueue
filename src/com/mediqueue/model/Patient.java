package com.mediqueue.model;

public class Patient {
    private String name;
    private int age;
    private String condition;
    private int priority;

    public Patient(String name, int age, String condition, int priority) {
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.priority = priority;
    }

    // Encapsulation
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public String getType() { return "General Patient"; }

    @Override
    public String toString() {
        return name + " - " + getType() + " - Age: " + age + " - Condition: " + condition + " - Priority: " + priority;
    }
}

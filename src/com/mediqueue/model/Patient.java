package com.mediqueue.model;

public abstract class Patient {
    // Encapsulation: private fields with getters/setters
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

    // Encapsulation: getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    // Polymorphism: enforce subclasses to implement their type
    public abstract String getType();

    @Override
    public String toString() {
        return name + " - " + getType() + " - Age: " + age + " - Condition: " + condition + " - Priority: " + priority;
    }
}

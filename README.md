# 🏥 MediQueue: Smart Patient Queue Management System

   Smart Patient Queue Management System


## 📌 Project Description

**MediQueue** is an intelligent Java-based system designed to manage patient queues efficiently.  
It applies **OOP concepts** like encapsulation, inheritance, polymorphism, and abstraction to simulate a real-world hospital workflow.  

Key points:

- **Encapsulation:** Patient data is stored securely with private fields and accessed through getters/setters.  
- **Inheritance:** Different patient types (*EmergencyPatient*, *RegularPatient*, *ScheduledPatient*) extend a base `Patient` class.  
- **Polymorphism:** Methods like `displayInfo()` and `getType()` behave differently depending on patient type.  
- **Abstraction:** System logic is separated into **model**, **service**, and **main** layers to hide complexity.  

The system allows staff to **add, update, remove, search, and display patients** while automatically prioritizing them based on urgency and appointment type.

---

## 🛠 Features

- Add new patient records  
- Edit existing patient data  
- Remove patients from the queue  
- Display all patients in queue with priority order  
- Automatically prioritize based on **age, condition, and type**  
- Console-based interface for easy interaction  

---

## 🧠 OOP Concepts in the System

| Concept | Example in Code | Purpose |
|---------|----------------|---------|
| Encapsulation | Private fields in `Patient.java` + getters/setters | Protects data and controls access |
| Inheritance | `EmergencyPatient`, `RegularPatient`, `ScheduledPatient` extend `Patient` | Reuse parent class fields and methods |
| Polymorphism | Overridden methods `displayInfo()` and `getType()` | Different behavior per patient type |
| Abstraction | Abstract class `Patient` and service layer `QueueManager` | Hides internal logic, simplifies system |
| Constructors | Parameterized constructors in all patient classes | Initialize objects with required data |
| super() | Used in subclasses | Calls parent constructor to set common fields |


---

## 👥 Collaborators

**Team Name: GipitHub**

| Name | Role |
|------|------|
| Jhon Lheo Salapare | Lead Developer / Logic Designer |
| VJ Reyes | Debugger / Feature Tester |
| John Lloyd Reyes | Debugger / Feature Tester |
| Ann Eleine Reyes | Concept Designer / Structure Planning |

---

## ▶️ How to Run

1. Clone or download the repository  
2. Open in an IDE (IntelliJ, NetBeans, or VS Code)  
3. Run `Main.java`  
4. Follow console prompts to add, edit, remove, or display patients  

---



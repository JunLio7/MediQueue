package com.mediqueue.ui;

import com.mediqueue.model.*;
import com.mediqueue.service.QueueManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class InputPanel extends JPanel {

    private final QueueManager queue;
    private final Runnable refreshCallback;
    private final TablePanel tablePanel;

    private final JTextField nameField, ageField, conditionField, priorityField, searchField;
    private final JComboBox<String> typeComboBox;
    private final JButton addButton, updateButton, deleteButton;
    private final JPanel searchPanel;

    public InputPanel(QueueManager queue, Runnable refreshCallback, TablePanel tablePanel) {
        this.queue = queue;
        this.refreshCallback = refreshCallback;
        this.tablePanel = tablePanel;

        setBackground(new Color(245, 245, 245));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = createTextField(150);
        ageField = createTextField(50);
        conditionField = createTextField(150);
        priorityField = createTextField(50);

        typeComboBox = new JComboBox<>(new String[]{"Regular", "Emergency", "Scheduled"});
        typeComboBox.setBackground(Color.WHITE);

        addButton = createButton("Add", new Color(76, 175, 80));
        updateButton = createButton("Update", new Color(33, 150, 243));
        deleteButton = createButton("Delete", new Color(244, 67, 54));

        // --- Add components ---
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; add(nameField, gbc);
        gbc.gridx = 2; add(new JLabel("Age:"), gbc);
        gbc.gridx = 3; add(ageField, gbc);
        gbc.gridx = 4; add(new JLabel("Condition:"), gbc);
        gbc.gridx = 5; add(conditionField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Type:"), gbc);
        gbc.gridx = 1; add(typeComboBox, gbc);
        gbc.gridx = 2; add(new JLabel("Priority:"), gbc);
        gbc.gridx = 3; add(priorityField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(addButton, gbc);
        gbc.gridx = 1; add(updateButton, gbc);
        gbc.gridx = 2; add(deleteButton, gbc);

        // Search panel
        searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.add(new JLabel("Search:"));
        searchField = createTextField(200);
        searchPanel.add(searchField);

        // --- Listeners ---
        typeComboBox.addActionListener(e -> updatePriorityField());
        addButton.addActionListener(e -> addPatient());
        updateButton.addActionListener(e -> openUpdateDialog());
        deleteButton.addActionListener(e -> deletePatient());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterTable(); }
        });

        updatePriorityField(); // initialize priority field
    }

    private JTextField createTextField(int width) {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(width, 25));
        tf.setBackground(Color.WHITE);
        return tf;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    public JPanel getSearchPanel() { return searchPanel; }

    private void updatePriorityField() {
        String type = (String) typeComboBox.getSelectedItem();
        switch(type) {
            case "Emergency" -> {
                priorityField.setText("1");
                priorityField.setEnabled(false);
            }
            case "Regular" -> {
                priorityField.setText("3");
                priorityField.setEnabled(false);
            }
            case "Scheduled" -> {
                priorityField.setText("");
                priorityField.setEnabled(true);
            }
        }
    }

    private void addPatient() {
        try {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String condition = conditionField.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            String priorityText = priorityField.getText().trim();

            if (name.isEmpty()) throw new Exception("Name cannot be empty!");
            if (condition.isEmpty()) throw new Exception("Condition cannot be empty!");

            int age;
            try { age = Integer.parseInt(ageText); }
            catch (NumberFormatException e) { throw new Exception("Age must be a number!"); }

            int priority;
            switch (type) {
                case "Scheduled" -> {
                    try { priority = Integer.parseInt(priorityText); }
                    catch (NumberFormatException e) { throw new Exception("Priority must be a number!"); }
                }
                case "Emergency" -> priority = 1;
                default -> priority = 3;
            }

            boolean exists = queue.getAllPatients().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
            if (exists) throw new Exception("Patient with this name already exists!");

            Patient p = switch (type) {
                case "Regular" -> new RegularPatient(name, age, condition);
                case "Emergency" -> new EmergencyPatient(name, age, condition);
                case "Scheduled" -> new ScheduledPatient(name, age, condition, Integer.parseInt(priorityText));
                default -> throw new Exception("Invalid type");
            };

            queue.addPatient(p);
            refreshCallback.run();
            clearInputs();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputs() {
        nameField.setText("");
        ageField.setText("");
        conditionField.setText("");
        priorityField.setText("");
        typeComboBox.setSelectedIndex(0);
        updatePriorityField();
    }

    private void deletePatient() {
        try {
            int row = tablePanel.getSelectedRow();
            if (row == -1) throw new Exception("Select a patient to delete!");
            queue.getAllPatients().remove(row);
            refreshCallback.run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterTable() {
        String query = searchField.getText().toLowerCase();
        List<Patient> filtered = queue.getAllPatients().stream()
                .filter(p -> p.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
        tablePanel.refreshTable(filtered);
    }

    private void openUpdateDialog() {
        int row = tablePanel.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Patient selected = queue.getAllPatients().get(row);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        UpdateDialog dialog = new UpdateDialog(parentFrame, selected, queue, v -> refreshCallback.run());
        dialog.setVisible(true);
    }
}

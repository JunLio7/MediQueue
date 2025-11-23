package com.mediqueue.ui;

import com.mediqueue.model.*;
import com.mediqueue.service.QueueManager;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class UpdateDialog extends JDialog {

    public UpdateDialog(JFrame parent, Patient selected, QueueManager queue, Consumer<Void> refreshCallback) {
        super(parent, "Update Patient", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(selected.getName());
        nameField.setEditable(false);
        nameField.setBackground(new Color(230, 230, 230));

        JTextField ageField = new JTextField(String.valueOf(selected.getAge()));
        ageField.setEditable(false);
        ageField.setBackground(new Color(230, 230, 230));

        JTextField conditionField = new JTextField(selected.getCondition());

        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Regular", "Emergency", "Scheduled"});
        typeCombo.setSelectedItem(selected.getType());

        JTextField priorityField = new JTextField(String.valueOf(selected.getPriority()));
        priorityField.setEnabled(selected.getType().equals("Scheduled"));

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(33, 150, 243));
        saveButton.setForeground(Color.WHITE);

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Age:"), gbc);
        gbc.gridx = 1; add(ageField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Condition:"), gbc);
        gbc.gridx = 1; add(conditionField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Type:"), gbc);
        gbc.gridx = 1; add(typeCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Priority:"), gbc);
        gbc.gridx = 1; add(priorityField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; add(saveButton, gbc);

        // Listeners
        typeCombo.addActionListener(e -> {
            String type = (String) typeCombo.getSelectedItem();
            switch (type) {
                case "Emergency" -> {
                    priorityField.setText("1");
                    priorityField.setEnabled(false);
                }
                case "Regular" -> {
                    priorityField.setText("3");
                    priorityField.setEnabled(false);
                }
                default -> {
                    // Scheduled
                    priorityField.setText("");
                    priorityField.setEnabled(true);
                }
            }
        });

        saveButton.addActionListener(e -> {
            String condition = conditionField.getText().trim();
            String type = (String) typeCombo.getSelectedItem();
            int priority;

            try {
                priority = Integer.parseInt(priorityField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Priority must be a number!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Patient updated;
            switch (type) {
                case "Regular" -> updated = new RegularPatient(selected.getName(), selected.getAge(), condition);
                case "Emergency" -> updated = new EmergencyPatient(selected.getName(), selected.getAge(), condition);
                case "Scheduled" -> updated = new ScheduledPatient(selected.getName(), selected.getAge(), condition, priority);
                default -> { return; }
            }

            int index = queue.getAllPatients().indexOf(selected);
            queue.getAllPatients().set(index, updated);
            refreshCallback.accept(null);
            dispose();
        });
    }
}

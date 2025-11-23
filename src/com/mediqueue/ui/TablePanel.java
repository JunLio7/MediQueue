package com.mediqueue.ui;

import com.mediqueue.model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel model;

    public TablePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        model = new DefaultTableModel(new String[]{"Name", "Age", "Condition", "Type", "Priority"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(28);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable(List<Patient> patients) {
        model.setRowCount(0);
        for (Patient p : patients) {
            model.addRow(new Object[]{p.getName(), p.getAge(), p.getCondition(), p.getType(), p.getPriority()});
        }
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }
}

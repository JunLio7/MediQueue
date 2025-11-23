package com.mediqueue.ui;

import com.mediqueue.service.QueueManager;

import javax.swing.*;
import java.awt.*;

public class MediQueueUI extends JFrame {

    private final QueueManager queue;
    private final InputPanel inputPanel;
    private final TablePanel tablePanel;

    public MediQueueUI() {
        super("MediQueue - Smart Patient Queue");

        queue = new QueueManager();

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 500);
        setLocationRelativeTo(null);

        try {
            Image icon = new ImageIcon(getClass().getResource("logo.png")).getImage();
            setIconImage(icon);
        } catch (Exception e) {
            System.out.println("App icon not found: " + e.getMessage());
        }

        // Panels
        tablePanel = new TablePanel();
        inputPanel = new InputPanel(queue, this::refreshTable, tablePanel);

        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(inputPanel.getSearchPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void refreshTable() {
        tablePanel.refreshTable(queue.getAllPatients());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MediQueueUI::new);
    }
}

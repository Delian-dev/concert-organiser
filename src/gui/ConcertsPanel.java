package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import models.Concert;
import services.ConcertService;

public class ConcertsPanel extends JPanel {
    private final JTable concertsTable;
    private final DefaultTableModel tableModel;

    public ConcertsPanel() {
        setLayout(new BorderLayout());

        // Header label
        JLabel header = new JLabel("🎤 Concerts", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        add(header, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"ID", "Concert Name", "Date", "Capacity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        concertsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(concertsTable);
        add(scrollPane, BorderLayout.CENTER); // put table in CENTER

        // Load button
        JButton loadConcerts = new JButton("Load Concerts");
        loadConcerts.addActionListener(e -> loadConcertsIntoTable());
        add(loadConcerts, BorderLayout.SOUTH);
    }

    private void loadConcertsIntoTable() {
        ConcertService concertService = new ConcertService();
        List<Concert> concerts = concertService.listConcerts();

        // Clear old data
        tableModel.setRowCount(0);

        // Populate table
        for (Concert c : concerts) {
            Object[] row = {
                    c.getConcertId(),
                    c.getConcertName(),
                    c.getDate(),
                    c.getCapacity()
            };
            tableModel.addRow(row);
        }
    }
}

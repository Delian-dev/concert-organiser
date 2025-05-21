package gui.panels;

import javax.swing.*;
import java.awt.*;

import db_methods.ConcertDbMethods;
import gui.MainFrame;
import models.Concert;

public class ConcertDetailsPanel extends JPanel {
    private final JLabel nameLabel = new JLabel();
    private final JLabel dateLabel = new JLabel();
    private final JLabel locationLabel = new JLabel();
    private final JTextArea descriptionArea = new JTextArea();

    private final MainFrame mainFrame;
    private Concert currentConcert;
    public ConcertDetailsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        dateLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        locationLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(nameLabel);
        topPanel.add(dateLabel);
        topPanel.add(locationLabel);

        // ⬅ Back Button
        JButton backButton = new JButton("⬅ Back");
        backButton.addActionListener(e -> mainFrame.showPanel("concerts"));

        // 🛠 Update Button
        JButton updateButton = new JButton("✏ Update");
        updateButton.addActionListener(e -> {
            gui.input.AddConcertPanel updatePanel = mainFrame.getAddConcertPanel();
            updatePanel.loadConcertForUpdate(currentConcert);
            mainFrame.showPanel("addConcert");
        });

        // ❌ Delete Button
        JButton deleteButton = new JButton("🗑 Delete");
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this, "Are you sure you want to delete this concert?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                ConcertDbMethods service = new ConcertDbMethods();
                service.deleteConcert(currentConcert.getConcertId());
                mainFrame.showPanel("concerts");
            }
        });

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public void setConcert(Concert concert) {
        this.currentConcert = concert;

        nameLabel.setText("🎵 " + concert.getConcertName());
        dateLabel.setText("🗓 Date: " + concert.getDate());
        locationLabel.setText("📍 Location: " + concert.getLocationId());
        descriptionArea.setText(concert.getConcertName() != null ? concert.getDate() : "No description provided.");
    }
}

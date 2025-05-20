package gui.panels;

import javax.swing.*;
import java.awt.*;

import gui.MainFrame;
import models.Concert;

public class ConcertDetailsPanel extends JPanel {
    private final JLabel nameLabel = new JLabel();
    private final JLabel dateLabel = new JLabel();
    private final JLabel locationLabel = new JLabel();
    private final JTextArea descriptionArea = new JTextArea();

    private final MainFrame mainFrame;

    public ConcertDetailsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(nameLabel);
        topPanel.add(dateLabel);
        topPanel.add(locationLabel);

        JButton backButton = new JButton("⬅ Back");
        backButton.addActionListener(e -> mainFrame.showPanel("concerts"));

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    public void setConcert(Concert concert) {
        nameLabel.setText("🎵 " + concert.getConcertName());
        dateLabel.setText("🗓 Date: " + concert.getDate());
        locationLabel.setText("📍 Location: " + concert.getLocationId());
        descriptionArea.setText(concert.getConcertName() != null ? concert.getDate() : "No description provided.");
    }
}

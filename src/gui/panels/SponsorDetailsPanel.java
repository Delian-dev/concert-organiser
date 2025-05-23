package gui.panels;

import db_methods.SponsorDbMethods;
import gui.MainFrame;
import models.Concert;
import models.Sponsor;
import services.SponsorService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SponsorDetailsPanel extends JPanel {
    private final JPanel contentPanel;
    private Sponsor currentSponsor;
    private Runnable onUpdateRequested;
    private Runnable onDeleted;

    private final JButton updateButton;
    private final JButton deleteButton;

    private final SponsorDbMethods sponsorDbMethods = new SponsorDbMethods();
    private final SponsorService sponsorService = new SponsorService();

    public SponsorDetailsPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("🏢 Sponsor Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 28));
        add(headerLabel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        updateButton = new JButton("✏ Update");
        deleteButton = new JButton("🗑 Delete");
        JButton backButton = new JButton("⬅ Back");

        backButton.addActionListener(e -> mainFrame.showPanel("sponsors"));
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            if (currentSponsor != null && onUpdateRequested != null) {
                onUpdateRequested.run();
            }
        });

        deleteButton.addActionListener(e -> {
            if (currentSponsor == null) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this sponsor?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    sponsorDbMethods.deleteSponsor(currentSponsor.getSponsorId());
                    JOptionPane.showMessageDialog(this, "Sponsor deleted successfully.");
                    if (onDeleted != null) onDeleted.run();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Delete failed: " + ex.getMessage());
                }
            }
        });
    }

    public void setSponsor(Sponsor sponsor, Runnable onUpdateRequested, Runnable onDeleted) {
        this.currentSponsor = sponsor;
        this.onUpdateRequested = onUpdateRequested;
        this.onDeleted = onDeleted;

        contentPanel.removeAll();

        if (sponsor == null) {
            JLabel noSponsorLabel = new JLabel("<html><i>No sponsor selected.</i></html>");
            noSponsorLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            contentPanel.add(noSponsorLabel);
        } else {
            addLabel("📛 Name: " + sponsor.getSponsorName());
            addLabel("💰 Market Value: $" + sponsor.getMarketValue());
            //addLabel("🏷 Type: " + sponsor.getSponsorType());

            contentPanel.add(Box.createVerticalStrut(20));
            contentPanel.add(createConcertsSection(sponsor.getSponsorId()));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(label);
        contentPanel.add(Box.createVerticalStrut(6));
    }

    private JPanel createConcertsSection(int sponsorId) {
        List<Concert> concerts = sponsorService.getConcertsBySponsorId(sponsorId);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "🎫 Sponsored Concerts",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI Emoji", Font.BOLD, 18),
                Color.DARK_GRAY
        ));

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 16);

        if (concerts.isEmpty()) {
            JLabel label = new JLabel("<html><i>No concerts for this sponsor yet</i></html>");
            label.setFont(font);
            panel.add(label);
        } else {
            for (Concert concert : concerts) {
                JLabel label = new JLabel("🎤 " + concert.getConcertName() + " — " + concert.getDate());
                label.setFont(font);
                label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                panel.add(label);
            }
        }

        return panel;
    }
}

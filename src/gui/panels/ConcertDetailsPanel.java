package gui.panels;

import db_methods.ConcertDbMethods;
import db_methods.LocationDbMethods;
import db_methods.MusicianConcertDbMethods;
import db_methods.SponsorConcertDbMethods;
import gui.MainFrame;
import models.*;
import services.ConcertService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ConcertDetailsPanel extends JPanel {
    private final JLabel nameLabel = new JLabel();
    private final JLabel dateLabel = new JLabel();
    private final JLabel locationLabel = new JLabel();

    private final ConcertService concertService = new ConcertService();
    private final JPanel detailsContainer = new JPanel();

    private final MainFrame mainFrame;
    private Concert currentConcert;

    public ConcertDetailsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 28));
        dateLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        locationLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(nameLabel);
        topPanel.add(dateLabel);
        topPanel.add(locationLabel);

        // Buttons
        JButton backButton = new JButton("⬅ Back");
        backButton.addActionListener(e -> mainFrame.showPanel("concerts"));

        JButton updateButton = new JButton("✏ Update");
        updateButton.addActionListener(e -> {
            gui.input.AddConcertPanel updatePanel = mainFrame.getAddConcertPanel();
            updatePanel.loadConcertForUpdate(currentConcert);
            mainFrame.showPanel("addConcert");
        });

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

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Main container (scrollable)
        detailsContainer.setLayout(new BoxLayout(detailsContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(detailsContainer);
        scrollPane.setBorder(null);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setConcert(Concert concert) {
        this.currentConcert = concert;

        nameLabel.setText("🎵 " + concert.getConcertName());
        dateLabel.setText("🗓 Date: " + concert.getDate());

        // Retrieve and format location
        LocationDbMethods locationService = new LocationDbMethods();
        Location location = locationService.selectLocationById(concert.getLocationId()); // Make sure Concert has getLocation()
        if (location != null) {
            locationLabel.setText("📍 " + location.getCity() + ", " + location.getAddress());
        } else {
            locationLabel.setText("📍 Location info not available");
        }

        detailsContainer.removeAll();

        // Add spacing between sections
        detailsContainer.add(Box.createVerticalStrut(20));
        detailsContainer.add(createSectionPanel("🎸 Performing Musicians", createMusiciansPanel()));
        detailsContainer.add(Box.createVerticalStrut(20));
        detailsContainer.add(createSectionPanel("💼 Sponsors", createSponsorsPanel()));
        detailsContainer.add(Box.createVerticalStrut(20));
        detailsContainer.add(createSectionPanel("🎫 Tickets", createTicketsPanel()));
        detailsContainer.add(Box.createVerticalStrut(20));

        detailsContainer.revalidate();
        detailsContainer.repaint();
    }

    private JPanel createSectionPanel(String title, JPanel content) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 18),
                Color.DARK_GRAY
        ));
        sectionPanel.add(content, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JPanel createMusiciansPanel() {
        List<Musician> musicians = concertService.listMusicians(currentConcert.getConcertId());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Font contentFont = new Font("Segoe UI Emoji", Font.PLAIN, 16);

        if (musicians.isEmpty()) {
            JLabel noMusicians = new JLabel("No musicians assigned.");
            noMusicians.setFont(contentFont);
            panel.add(noMusicians);
        } else {
            for (Musician m : musicians) {
                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);
                row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JLabel label = new JLabel("🎤 " + m.getName() + " — " + m.getGenre());
                label.setFont(contentFont);

                JButton deleteBtn = new JButton("X");
                styleDeleteButton(deleteBtn);
                deleteBtn.setToolTipText("Remove musician from concert");

                deleteBtn.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Remove " + m.getName() + " from this concert?",
                            "Confirm Remove",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        new MusicianConcertDbMethods().deleteMusicianConcert(currentConcert.getConcertId(), m.getMusicianId());
                        setConcert(currentConcert); // Refresh
                    }
                });

                row.add(label);
                row.add(Box.createRigidArea(new Dimension(10, 0)));
                row.add(deleteBtn);
                panel.add(row);
            }
        }

        panel.add(Box.createVerticalStrut(10));

        JButton associateButton = new JButton("➕ Associate Musician");
        associateButton.addActionListener(e -> {
            mainFrame.getAssociateMusicianConcertPanel().setConcert(currentConcert);
            mainFrame.showPanel("associateMusician");
        });
        panel.add(associateButton);

        return panel;
    }

    private JPanel createSponsorsPanel() {
        Map<Sponsor, SponsorType> sponsors = concertService.listSponsors(currentConcert.getConcertId());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Font contentFont = new Font("Segoe UI Emoji", Font.PLAIN, 16);

        if (sponsors.isEmpty()) {
            JLabel noSponsors = new JLabel("No sponsors for this concert.");
            noSponsors.setFont(contentFont);
            panel.add(noSponsors);
        } else {
            for (Map.Entry<Sponsor, SponsorType> entry : sponsors.entrySet()) {
                Sponsor sponsor = entry.getKey();
                SponsorType type = entry.getValue();

                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                row.setAlignmentX(Component.LEFT_ALIGNMENT);
                row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JLabel label = new JLabel("🏢 " + sponsor.getSponsorName() + " — " + type.name());
                label.setFont(contentFont);

                JButton deleteBtn = new JButton("X");
                styleDeleteButton(deleteBtn);
                deleteBtn.setToolTipText("Remove sponsor from concert");

                deleteBtn.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "Remove " + sponsor.getSponsorName() + " from this concert?",
                            "Confirm Remove",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        new SponsorConcertDbMethods().deleteSponsorConcert(currentConcert.getConcertId(), sponsor.getSponsorId());
                        setConcert(currentConcert); // Refresh
                    }
                });

                row.add(label);
                row.add(Box.createRigidArea(new Dimension(10, 0)));
                row.add(deleteBtn);
                panel.add(row);
            }
        }

        panel.add(Box.createVerticalStrut(10));

        JButton associateButton = new JButton("➕ Associate Sponsor");
        associateButton.addActionListener(e -> {
            mainFrame.getAssociateSponsorConcertPanel().setConcert(currentConcert);
            mainFrame.showPanel("associateSponsor");
        });
        panel.add(associateButton);

        return panel;
    }



    private JPanel createTicketsPanel() {
        List<Ticket> tickets = concertService.listTickets(currentConcert.getConcertId());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Font contentFont = new Font("Segoe UI Emoji", Font.PLAIN, 16);

        if (tickets.isEmpty()) {
            JLabel noTickets = new JLabel("No tickets issued.");
            noTickets.setFont(contentFont);
            panel.add(noTickets);
        } else {
            for (Ticket ticket : tickets) {
                JLabel label = new JLabel("🎟 " + ticket.getTicketType() + " — $" + ticket.getPrice()
                        + " — Purchased: " + ticket.getTransactionDate());
                label.setFont(contentFont);
                panel.add(label);
            }
        }
        return panel;
    }

    private void styleDeleteButton(JButton deleteBtn) {
        deleteBtn.setPreferredSize(new Dimension(20, 20));
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 10));
        deleteBtn.setForeground(Color.RED);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorder(BorderFactory.createLineBorder(Color.RED));
        deleteBtn.setContentAreaFilled(true);
        deleteBtn.setBackground(Color.WHITE);
    }

}

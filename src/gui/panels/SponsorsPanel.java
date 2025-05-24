package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import gui.MainFrame;
import models.Sponsor;
import db_methods.SponsorDbMethods;

import gui.components.*;

public class SponsorsPanel extends JPanel {
    private final JPanel sponsorsListPanel;
    private final MainFrame mainFrame;

    public SponsorsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("💼 Sponsors", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        add(header, BorderLayout.NORTH);

        // List panel
        sponsorsListPanel = new JPanel();
        sponsorsListPanel.setLayout(new BoxLayout(sponsorsListPanel, BoxLayout.Y_AXIS));
        sponsorsListPanel.setBackground(Color.WHITE);
        sponsorsListPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(sponsorsListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton loadSponsorsBtn = new JButton("Load Sponsors");
        JButton addSponsorBtn = new JButton("Add Sponsor");

        loadSponsorsBtn.addActionListener(e -> loadSponsorsIntoList());
        addSponsorBtn.addActionListener(e -> mainFrame.showPanel("addSponsor"));

        buttonsPanel.add(loadSponsorsBtn);
        buttonsPanel.add(addSponsorBtn);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void loadSponsorsIntoList() {
        SponsorDbMethods sponsorService = SponsorDbMethods.getInstance();
        List<Sponsor> sponsors = sponsorService.selectAll();

        sponsorsListPanel.removeAll();

        for (Sponsor s : sponsors) {
            JPanel sponsorPanel = new RoundedPanel();
            sponsorPanel.setLayout(new BorderLayout());
            sponsorPanel.setMaximumSize(new Dimension(500, 60));
            sponsorPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            sponsorPanel.setBackground(Color.LIGHT_GRAY);

            JLabel nameLabel = new JLabel(s.getSponsorName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            sponsorPanel.add(nameLabel, BorderLayout.CENTER);

            // Hover effect
            sponsorPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    sponsorPanel.setBackground(new Color(131, 227, 131));
                    sponsorPanel.setBorder(BorderFactory.createCompoundBorder(
                            new RoundedBorder(15, 3, Color.GREEN),
                            BorderFactory.createEmptyBorder(10, 20, 10, 20)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    sponsorPanel.setBackground(Color.LIGHT_GRAY);
                    sponsorPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    mainFrame.showSponsorDetails(s);
                }
            });

            sponsorsListPanel.add(sponsorPanel);
            sponsorsListPanel.add(Box.createVerticalStrut(25)); // spacing between items
        }

        sponsorsListPanel.revalidate();
        sponsorsListPanel.repaint();
    }
}
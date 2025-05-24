package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gui.MainFrame;
import models.Sponsor;
import db_methods.SponsorDbMethods;
import services.SponsorService;
import gui.components.*;

public class SponsorsPanel extends JPanel {
    private final JPanel sponsorsListPanel;
    private final MainFrame mainFrame;
    private boolean sortAscending = true;
    private final SponsorService sponsorService = new SponsorService();

    public SponsorsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Top panel with header and sort dropdown
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        JLabel header = new JLabel("💼 Sponsors");
        header.setFont(new Font("SEGOE UI EMOJI", Font.BOLD, 24));
        topPanel.add(header, BorderLayout.WEST);

        String[] sortOptions = {"Sort by Concert Count ↑", "Sort by Concert Count ↓"};
        JComboBox<String> sortDropdown = new JComboBox<>(sortOptions);
        sortDropdown.setMaximumSize(new Dimension(180, 30));

        sortDropdown.addActionListener(e -> {
            sortAscending = sortDropdown.getSelectedIndex() == 0;
            loadSponsorsIntoList();
        });

        topPanel.add(sortDropdown, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Sponsors list
        sponsorsListPanel = new JPanel();
        sponsorsListPanel.setLayout(new BoxLayout(sponsorsListPanel, BoxLayout.Y_AXIS));
        sponsorsListPanel.setBackground(Color.WHITE);
        sponsorsListPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(sponsorsListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Bottom button panel
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
        List<Sponsor> sponsors = sponsorService.getSponsorsSortedByConcertCount();
        if (sortAscending) {
            Collections.reverse(sponsors);
        }

        sponsorsListPanel.removeAll();

        for (Sponsor s : sponsors) {
            int concertCount = sponsorService.getConcertCountBySponsorId(s.getSponsorId());

            JPanel sponsorPanel = new RoundedPanel();
            sponsorPanel.setLayout(new BorderLayout());
            sponsorPanel.setMaximumSize(new Dimension(500, 80));
            sponsorPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            sponsorPanel.setBackground(Color.LIGHT_GRAY);

            // Name label (title)
            JLabel nameLabel = new JLabel("💼 " + s.getSponsorName(), SwingConstants.LEFT);
            nameLabel.setFont(new Font("SEGOE UI EMOJI", Font.BOLD, 18));

            // Concert info label (subtext)
            JLabel concertInfoLabel = new JLabel("No. of concerts sponsored: " + concertCount, SwingConstants.LEFT);
            concertInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            concertInfoLabel.setForeground(Color.DARK_GRAY);

            // Vertical box to hold both labels
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setOpaque(false);
            textPanel.add(nameLabel);
            textPanel.add(concertInfoLabel);

            sponsorPanel.add(textPanel, BorderLayout.CENTER);

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


    @Override
    public void addNotify() {
        super.addNotify();
        loadSponsorsIntoList();
    }
}

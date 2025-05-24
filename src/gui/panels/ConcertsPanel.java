package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import gui.MainFrame;
import models.Concert;
import services.ConcertService;
import gui.components.*;

public class ConcertsPanel extends JPanel {
    private final JPanel concertsListPanel;
    private final MainFrame mainFrame;
    private boolean sortAscending = true;
    private final ConcertService concertService = new ConcertService();

    public ConcertsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Top panel with header and sort dropdown
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        JLabel header = new JLabel("🎤 Concerts");
        header.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(header, BorderLayout.WEST);

        String[] sortOptions = {"Sort by Date ↑", "Sort by Date ↓"};
        JComboBox<String> sortDropdown = new JComboBox<>(sortOptions);
        sortDropdown.setMaximumSize(new Dimension(150, 30));

        sortDropdown.addActionListener(e -> {
            sortAscending = sortDropdown.getSelectedIndex() == 0;
            services.CSV_Service.logAction("SORT-BY-DATE", "CONCERT");
            loadConcertsIntoList();
        });

        topPanel.add(sortDropdown, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // List panel
        concertsListPanel = new JPanel();
        concertsListPanel.setLayout(new BoxLayout(concertsListPanel, BoxLayout.Y_AXIS));
        concertsListPanel.setBackground(Color.WHITE);
        concertsListPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(concertsListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Bottom button panel
        JPanel buttonsPanel = new JPanel();
        JButton loadConcerts = new JButton("Load Concerts");
        JButton addConcertBtn = new JButton("Add Concert");

        loadConcerts.addActionListener(e -> loadConcertsIntoList());
        addConcertBtn.addActionListener(e -> mainFrame.showPanel("addConcert"));

        buttonsPanel.add(loadConcerts);
        buttonsPanel.add(addConcertBtn);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void loadConcertsIntoList() {
        List<Concert> concerts = concertService.listConcertsSortedByDate();

        if (!sortAscending) {
            Collections.reverse(concerts);
        }

        concertsListPanel.removeAll();


        for (Concert c : concerts) {
            JPanel concertPanel = new RoundedPanel();
            concertPanel.setLayout(new BorderLayout());
            concertPanel.setMaximumSize(new Dimension(500, 60));
            concertPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            concertPanel.setBackground(Color.LIGHT_GRAY);

            String labelText = c.getConcertName();
            JLabel nameLabel = new JLabel(labelText, SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            concertPanel.add(nameLabel, BorderLayout.CENTER);

            concertPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    concertPanel.setBackground(new Color(200, 200, 255));
                    concertPanel.setBorder(BorderFactory.createCompoundBorder(
                            new RoundedBorder(15, 3, Color.GREEN),
                            BorderFactory.createEmptyBorder(10, 20, 10, 20)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    concertPanel.setBackground(Color.LIGHT_GRAY);
                    concertPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    mainFrame.getConcertDetailsPanel().setConcert(c);
                    mainFrame.showPanel("concertDetailsForm");
                }
            });

            concertsListPanel.add(concertPanel);
            concertsListPanel.add(Box.createVerticalStrut(25));
        }

        concertsListPanel.revalidate();
        concertsListPanel.repaint();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        loadConcertsIntoList();
    }
}

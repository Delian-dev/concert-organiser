package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import gui.MainFrame;
import models.Concert;
import services.ConcertService;

import gui.components.*;

public class ConcertsPanel extends JPanel {
    private final JPanel concertsListPanel;  // container for concert panels
    private final MainFrame mainFrame;

    public ConcertsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // header
        JLabel header = new JLabel("🎤 Concerts", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        add(header, BorderLayout.NORTH);

        // panel for all the concerts
        concertsListPanel = new JPanel();
        concertsListPanel.setLayout(new BoxLayout(concertsListPanel, BoxLayout.Y_AXIS));
        concertsListPanel.setBackground(Color.WHITE);
        concertsListPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));


        JScrollPane scrollPane = new JScrollPane(concertsListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // buttons panel (down on the page)
        JPanel buttonsPanel = new JPanel();
        JButton loadConcerts = new JButton("Load Concerts");
        JButton addConcertBtn = new JButton("Add Concert");

        loadConcerts.addActionListener(e -> loadConcertsIntoList());
        addConcertBtn.addActionListener(e -> mainFrame.showPanel("addConcert"));

        buttonsPanel.add(loadConcerts);
        buttonsPanel.add(addConcertBtn);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadConcertsIntoList() {
        ConcertService concertService = new ConcertService();
        List<Concert> concerts = concertService.listConcerts();

        concertsListPanel.removeAll();

        for (Concert c : concerts) {
            JPanel concertPanel = new RoundedPanel();
            concertPanel.setLayout(new BorderLayout());
            concertPanel.setMaximumSize(new Dimension(500, 60));
            concertPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            concertPanel.setBackground(Color.LIGHT_GRAY);

            JLabel nameLabel = new JLabel(c.getConcertName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            concertPanel.add(nameLabel, BorderLayout.CENTER);

            // Hover effect
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
                    mainFrame.showPanel("concertDetails");
                }
            });

            concertsListPanel.add(concertPanel);
            concertsListPanel.add(Box.createVerticalStrut(25)); // space between panels
        }

        concertsListPanel.revalidate();
        concertsListPanel.repaint();
    }

}

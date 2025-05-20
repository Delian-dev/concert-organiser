package gui;

import gui.input.*;
import gui.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final ConcertDetailsPanel concertDetailsPanel;

    public MainFrame() {
        setTitle("Concert Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Main layout: sidebar + content
        setLayout(new BorderLayout());

        // Sidebar panel with nav buttons
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0, 1));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton homeButton = new JButton("Home");
        JButton sponsorsBtn = new JButton("Sponsors");
        JButton concertsBtn = new JButton("Concerts");
        JButton musicianBtn = new JButton("Musicians");
        JButton clientsBtn = new JButton("Clients");
        JButton addSponsorBtn = new JButton("Add Sponsor");
        JButton addConcertBtn = new JButton("Add Concert");
        JButton addMusicianBtn = new JButton("Add Musician");
        JButton addClientBtn = new JButton("Add Client");
        JButton countriesBtn = new JButton("Countries");

        //sidebar.add(dashboardBtn);
        sidebar.add(homeButton);
        sidebar.add(musicianBtn);
        sidebar.add(sponsorsBtn);
        sidebar.add(concertsBtn);
        //sidebar.add(addConcertBtn);
        sidebar.add(clientsBtn);
        sidebar.add(countriesBtn);
        //sidebar.add(ticketsBtn);

        add(sidebar, BorderLayout.WEST);

        // Content panel using CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        //contentPanel.add(new DashboardPanel(), "dashboard");
        contentPanel.add(new HomePanel(), "home");
        contentPanel.add(new SponsorsPanel(this), "sponsors");
        contentPanel.add(new ConcertsPanel(this), "concerts");
        contentPanel.add(new MusiciansPanel(this), "musicians");
        contentPanel.add(new ClientsPanel(this), "clients");
        contentPanel.add(new CountriesPanel(this), "countries");
        contentPanel.add(new AddConcertPanel(), "addConcert");
        contentPanel.add(new AddSponsorPanel(), "addSponsor");
        contentPanel.add(new AddMusicianPanel(), "addMusician");
        contentPanel.add(new AddClientPanel(), "addClient");
        contentPanel.add(new AddLocationPanel(this), "locationForm");

        concertDetailsPanel = new ConcertDetailsPanel(this);
        contentPanel.add(concertDetailsPanel, "concertDetailsForm");
        //contentPanel.add(new TicketsPanel(), "tickets");

        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "home");

        // Button listeners
        //dashboardBtn.addActionListener(e -> cardLayout.show(contentPanel, "dashboard"));
        homeButton.addActionListener(e -> cardLayout.show(contentPanel, "home"));
        musicianBtn.addActionListener(e -> cardLayout.show(contentPanel, "musicians"));
        sponsorsBtn.addActionListener(e -> cardLayout.show(contentPanel, "sponsors"));
        concertsBtn.addActionListener(e -> cardLayout.show(contentPanel, "concerts"));
        clientsBtn.addActionListener(e -> cardLayout.show(contentPanel, "clients"));
        addMusicianBtn.addActionListener(e -> cardLayout.show(contentPanel, "addMusician"));
        addConcertBtn.addActionListener(e -> cardLayout.show(contentPanel, "addConcert"));
        addSponsorBtn.addActionListener(e -> cardLayout.show(contentPanel, "addSponsor"));
        addClientBtn.addActionListener(e -> cardLayout.show(contentPanel, "addClient"));
        countriesBtn.addActionListener(e -> cardLayout.show(contentPanel, "countries"));
        //ticketsBtn.addActionListener(e -> cardLayout.show(contentPanel, "tickets"));

        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }

    public ConcertDetailsPanel getConcertDetailsPanel() {
        return concertDetailsPanel;
    }
}

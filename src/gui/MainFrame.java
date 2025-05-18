package gui;

import gui.input.AddConcertPanel;
import gui.input.AddLocationPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

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

        JButton dashboardBtn = new JButton("Dashboard");
        JButton concertsBtn = new JButton("Concerts");
        JButton ticketsBtn = new JButton("Tickets");
        JButton addConcertBtn = new JButton("Add Concert");
        JButton countriesBtn = new JButton("Countries");

        //sidebar.add(dashboardBtn);
        sidebar.add(concertsBtn);
        sidebar.add(addConcertBtn);
        sidebar.add(countriesBtn);
        //sidebar.add(ticketsBtn);

        add(sidebar, BorderLayout.WEST);

        // Content panel using CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        //contentPanel.add(new DashboardPanel(), "dashboard");
        contentPanel.add(new ConcertsPanel(), "concerts");
        contentPanel.add(new AddConcertPanel(), "addConcert");
        contentPanel.add(new CountriesPanel(this), "countries");
        contentPanel.add(new AddLocationPanel(this), "locationForm");
        //contentPanel.add(new TicketsPanel(), "tickets");

        add(contentPanel, BorderLayout.CENTER);

        // Button listeners
        //dashboardBtn.addActionListener(e -> cardLayout.show(contentPanel, "dashboard"));
        concertsBtn.addActionListener(e -> cardLayout.show(contentPanel, "concerts"));
        addConcertBtn.addActionListener(e -> cardLayout.show(contentPanel, "addConcert"));
        countriesBtn.addActionListener(e -> cardLayout.show(contentPanel, "countries"));
        //ticketsBtn.addActionListener(e -> cardLayout.show(contentPanel, "tickets"));

        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }
}

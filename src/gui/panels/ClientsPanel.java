package gui.panels;

import db_methods.ClientDbMethods;
import db_methods.ConcertDbMethods;
import db_methods.TicketDbMethods;
import gui.MainFrame;
import models.Client;
import models.Concert;
import models.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientsPanel extends JPanel {
    private final ClientDbMethods clientService = new ClientDbMethods();
    private final TicketDbMethods ticketService = new TicketDbMethods();
    private final ConcertDbMethods concertService = new ConcertDbMethods();
    private final JPanel clientsListPanel = new JPanel();
    private final MainFrame mainFrame;

    public ClientsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel header = new JLabel("👥 Clients & Tickets", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        add(header, BorderLayout.NORTH);

        clientsListPanel.setLayout(new BoxLayout(clientsListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(clientsListPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loadBtn = new JButton("Load Clients");
        JButton addBtn = new JButton("Add Client");

        loadBtn.addActionListener(e -> loadClients());
        addBtn.addActionListener(e -> mainFrame.showPanel("addClient"));

        buttonPanel.add(loadBtn);
        buttonPanel.add(addBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadClients() {
        clientsListPanel.removeAll();
        List<Client> clients = clientService.selectAll();

        clientsListPanel.add(Box.createVerticalStrut(20));

        for (Client client : clients) {
            clientsListPanel.add(createClientPanel(client));
            clientsListPanel.add(Box.createVerticalStrut(20));
        }

        clientsListPanel.revalidate();
        clientsListPanel.repaint();
    }

    private JPanel createClientPanel(Client client) {
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));
        clientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        clientPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // === HEADER ROW ===
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setOpaque(false);

        JButton expandButton = new JButton("▶"); // ▶ collapsed by default
        expandButton.setFont(new Font("Dialog", Font.BOLD, 14));
        expandButton.setFocusPainted(false);
        expandButton.setMargin(new Insets(0, 6, 0, 6));
        expandButton.setMargin(new Insets(2, 8, 2, 8));
        expandButton.setFocusable(false);

        JLabel nameLabel = new JLabel(client.getUsername() + " (" + client.getEmail() + ")");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton editButton = new JButton("Edit");
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        editButton.setMargin(new Insets(2, 8, 2, 8));
        editButton.setFocusable(false);
        editButton.setToolTipText("Edit client");
        editButton.addActionListener(e -> {
            mainFrame.getUpdateClientPanel().setClient(client, this::loadClients);
            mainFrame.showPanel("updateClient");
        });

        headerPanel.add(expandButton);
        headerPanel.add(Box.createRigidArea(new Dimension(8, 0)));
        headerPanel.add(nameLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(editButton);

        // === TICKETS PANEL ===
        JPanel ticketsPanel = new JPanel();
        ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));
        ticketsPanel.setVisible(false);
        ticketsPanel.setOpaque(false);

        expandButton.addActionListener(e -> toggleTicketsPanel(client, ticketsPanel, expandButton));

        clientPanel.add(headerPanel);
        clientPanel.add(Box.createVerticalStrut(5));
        clientPanel.add(ticketsPanel);

        return clientPanel;
    }



    private void toggleTicketsPanel(Client client, JPanel ticketsPanel, JButton expandButton) {
        boolean showing = ticketsPanel.isVisible();
        ticketsPanel.setVisible(!showing);
        expandButton.setText(showing ? "▶" : "▼"); // ▶ or ▼
        expandButton.revalidate();
        expandButton.repaint();

        if (!showing) {
            List<Ticket> tickets = ticketService.selectTicketsByClientId(client.getclientId());
            ticketsPanel.removeAll();

            if (tickets.isEmpty()) {
                ticketsPanel.add(new JLabel("No tickets found."));
            } else {
                for (Ticket t : tickets) {
                    String concertName = "Unknown Concert";
                    try {
                        Concert concert = concertService.selectConcertById(t.getConcertId());
                        if (concert != null) concertName = concert.getConcertName();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    ticketsPanel.add(new JLabel("🎫 " + t.getTicketType() + " - $" + t.getPrice() + " - " + concertName));
                }
            }

            JButton addTicketButton = new JButton("➕ Add Ticket");
            addTicketButton.addActionListener(e -> {
                mainFrame.getAddTicketPanel().setClient(client);
                mainFrame.showPanel("addTicket");
            });

            ticketsPanel.add(Box.createVerticalStrut(10));
            ticketsPanel.add(addTicketButton);
            ticketsPanel.revalidate();
            ticketsPanel.repaint();
        }
    }

}

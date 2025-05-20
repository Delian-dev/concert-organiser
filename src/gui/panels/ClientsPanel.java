package gui.panels;

import db_methods.ClientDbMethods;
import models.Client;
import models.Ticket;
import models.TicketType;
import db_methods.TicketDbMethods;

import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientsPanel extends JPanel {
    private final ClientDbMethods clientService = new ClientDbMethods();
    private final TicketDbMethods ticketService = new TicketDbMethods();

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

    private void loadClients() {
        clientsListPanel.removeAll();
        List<Client> clients = clientService.selectAll();

        for (Client client : clients) {
            JPanel clientPanel = new JPanel();
            clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));
            clientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            clientPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(""),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            JButton expandButton = new JButton("▼");
            expandButton.setPreferredSize(new Dimension(40, 30));

            JLabel nameLabel = new JLabel(client.getUsername() + " (" + client.getEmail() + ")", SwingConstants.LEFT);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

            headerPanel.add(expandButton, BorderLayout.WEST);
            headerPanel.add(nameLabel, BorderLayout.CENTER);

            JPanel ticketsPanel = new JPanel();
            ticketsPanel.setLayout(new BoxLayout(ticketsPanel, BoxLayout.Y_AXIS));
            ticketsPanel.setVisible(false);

            clientPanel.add(headerPanel);
            clientPanel.add(ticketsPanel);

            expandButton.addActionListener(e -> {
                if (ticketsPanel.isVisible()) {
                    ticketsPanel.setVisible(false);
                    expandButton.setText("▶");
                } else {
                    List<Ticket> tickets = ticketService.selectTicketsByClientId(client.getclientId());
                    ticketsPanel.removeAll();

                    if (tickets.isEmpty()) {
                        ticketsPanel.add(new JLabel("No tickets found."));
                    } else {
                        for (Ticket t : tickets) {
                            ticketsPanel.add(new JLabel("🎫 " + t.getTicketType() + " - $" + t.getPrice() + " - " + t.getTransactionDate()));
                        }
                    }

                    ticketsPanel.setVisible(true);
                    expandButton.setText("▼");
                    ticketsPanel.revalidate();
                }
            });

            clientsListPanel.add(clientPanel);
            clientsListPanel.add(Box.createVerticalStrut(20));
        }

        clientsListPanel.revalidate();
        clientsListPanel.repaint();
    }
}

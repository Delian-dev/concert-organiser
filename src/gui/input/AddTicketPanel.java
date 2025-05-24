package gui.input;

import db_methods.ConcertDbMethods;
import db_methods.TicketDbMethods;
import models.Client;
import models.Concert;
import models.Ticket;
import models.TicketType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AddTicketPanel extends JPanel {
    private final JComboBox<Concert> concertDropdown = new JComboBox<>();
    private final JComboBox<TicketType> typeDropdown = new JComboBox<>(TicketType.values());
    private final JTextField priceField = new JTextField(8);  // consistent size with AddConcertPanel
    private final JButton saveButton = new JButton("Save Ticket");

    private final ConcertDbMethods concertDb = new ConcertDbMethods();
    private final TicketDbMethods ticketDb = new TicketDbMethods();

    private Client client;

    public AddTicketPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Concert Dropdown
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Select Concert:"), gbc);
        gbc.gridx = 1;
        add(concertDropdown, gbc);

        // Ticket Type Dropdown
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Select Ticket Type:"), gbc);
        gbc.gridx = 1;
        add(typeDropdown, gbc);

        // Price Field
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Enter Price:"), gbc);
        gbc.gridx = 1;
        add(priceField, gbc);

        // Save Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton.setPreferredSize(new Dimension(140, 30));
        add(saveButton, gbc);

        saveButton.addActionListener(e -> saveTicket());
    }

    public void setClient(Client client) {
        this.client = client;
        loadConcerts();
    }

    private void loadConcerts() {
        concertDropdown.removeAllItems();
        List<Concert> concerts = concertDb.selectAll();
        for (Concert concert : concerts) {
            concertDropdown.addItem(concert);
        }
    }

    private void saveTicket() {
        Concert selectedConcert = (Concert) concertDropdown.getSelectedItem();
        TicketType selectedType = (TicketType) typeDropdown.getSelectedItem();
        String priceText = priceField.getText().trim();

        if (selectedConcert == null || selectedType == null || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceText);
            if (price <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a positive number.");
            return;
        }

        String transactionDate = LocalDate.now().toString();

        Ticket newTicket = new Ticket(
                selectedConcert.getConcertId(),
                client.getclientId(),
                price,
                selectedType,
                transactionDate
        );

        ticketDb.insertTicket(newTicket);
        JOptionPane.showMessageDialog(this, "Ticket added successfully!");
        services.CSV_Service.logAction("INSERT", "TICKET");
        priceField.setText("");
    }
}

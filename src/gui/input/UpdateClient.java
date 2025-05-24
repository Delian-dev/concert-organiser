package gui.input;

import db_methods.ClientDbMethods;
import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneException;
import models.Client;
import validations.EmailValidator;
import validations.PhoneValidator;

import javax.swing.*;
import java.awt.*;

public class UpdateClient extends JPanel {
    private final JTextField nameField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField phoneField = new JTextField(20);
    private final ClientDbMethods clientDbMethods = new ClientDbMethods();
    private final EmailValidator emailValidator = new EmailValidator();
    private final PhoneValidator phoneValidator = new PhoneValidator();

    private Client currentClient;
    private Runnable onClientUpdated;

    public UpdateClient() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        JLabel titleLabel = new JLabel("Update Client");
        titleLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        // Name
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Name: "), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Email: "), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Phone: "), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(phoneField, gbc);

        // Button
        JButton updateButton = new JButton("✅ Update");
        updateButton.addActionListener(e -> handleUpdate());

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(updateButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private void handleUpdate() {
        if (currentClient == null) return;

        String newName = nameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newPhone = phoneField.getText().trim();

        try {
            if (!emailValidator.isValid(newEmail)) {
                throw new InvalidEmailException("Invalid email: " + newEmail);
            }
            if (!phoneValidator.isValid(newPhone)) {
                throw new InvalidPhoneException("Invalid phone: " + newPhone);
            }

            currentClient.setUsername(newName);
            currentClient.setEmail(newEmail);
            currentClient.setPhone(newPhone);

            clientDbMethods.updateClient(currentClient);
            JOptionPane.showMessageDialog(this, "Client updated successfully!");
            services.CSV_Service.logAction("UPDATE", "CLIENT"); //csv service

            if (onClientUpdated != null) onClientUpdated.run();

        } catch (InvalidEmailException | InvalidPhoneException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setClient(Client client, Runnable onUpdatedCallback) {
        this.currentClient = client;
        this.onClientUpdated = onUpdatedCallback;

        nameField.setText(client.getUsername());
        emailField.setText(client.getEmail());
        phoneField.setText(client.getPhone());
    }
}

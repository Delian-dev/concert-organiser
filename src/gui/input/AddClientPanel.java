package gui.input;

import models.Client;
import validations.EmailValidator;
import validations.PhoneValidator;
import exceptions.InvalidEmailException;
import exceptions.InvalidPhoneException;
import db_methods.ClientDbMethods;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddClientPanel extends JPanel {
    private final JTextField nameField;
    private final JTextField ageField;
    private final JTextField emailField;
    private final JTextField phoneField;
    private final JLabel messageLabel;

    private final ClientDbMethods clientService = new ClientDbMethods();

    public AddClientPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Age
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(20);
        add(ageField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        // Phone
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);

        // Message label for validation errors
        gbc.gridx = 1; gbc.gridy = 4;
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        add(messageLabel, gbc);

        // Submit button
        gbc.gridx = 1; gbc.gridy = 5;
        JButton submitButton = new JButton("Add Client");
        add(submitButton, gbc);

        submitButton.addActionListener(e -> handleSubmit());
    }

    private void handleSubmit() {
        try{
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            messageLabel.setText("");

            if (name.isEmpty()) {
                messageLabel.setText("Name cannot be empty.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
                if (age <= 0) {
                    messageLabel.setText("Age must be a positive number.");
                    return;
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid age number.");
                return;
            }

            EmailValidator emailValidator = new EmailValidator();
            if (!emailValidator.isValid(email)) {
                throw new InvalidEmailException("Invalid email address.");
            }

            PhoneValidator phoneValidator = new PhoneValidator();
            if (!phoneValidator.isValid(phone)) {
                throw new InvalidPhoneException("Invalid phone number.");
            }

            Client client = new Client(name, age, email, phone);

                clientService.insertClient(client);
                JOptionPane.showMessageDialog(this, "Client added successfully!");

                // Clear fields
                nameField.setText("");
                ageField.setText("");
                emailField.setText("");
                phoneField.setText("");
                messageLabel.setText("");
        }catch(InvalidEmailException e) {
            JOptionPane.showMessageDialog(this, "Invalid email address.");
        }
        catch(InvalidPhoneException e) {
            JOptionPane.showMessageDialog(this, "Invalid phone number.");
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + e.getMessage());
        }
    }
}

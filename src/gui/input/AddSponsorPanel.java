package gui.input;
import db_methods.SponsorDbMethods;
import models.Sponsor;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddSponsorPanel extends JPanel {
    private final SponsorDbMethods sponsorService = new SponsorDbMethods();

    public AddSponsorPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Sponsor Name
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Sponsor Name:"), gbc);
        gbc.gridx = 1;
        JTextField sponsorNameField = new JTextField(20);
        add(sponsorNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("MarketValue:"), gbc);
        gbc.gridx = 1;
        JTextField sponsorMarketValueField = new JTextField(20);
        add(sponsorMarketValueField, gbc);

        // Submit button
        gbc.gridx = 1; gbc.gridy = 6;
        JButton submitButton = new JButton("Add Sponsor");
        add(submitButton, gbc);

        // Submit handler
        submitButton.addActionListener(e -> {
            String name = sponsorNameField.getText();
            String marketValueText = sponsorMarketValueField.getText();

            if (name.isEmpty() || marketValueText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields!");
                return;
            }

            try {
                long marketValue = Long.parseLong(marketValueText);
                Sponsor sponsor = new Sponsor(name, marketValue);
                sponsorService.insertSponsor(sponsor);
                JOptionPane.showMessageDialog(this, "Sponsor added successfully!");
                sponsorNameField.setText(""); //resets the fields after submit attempt
                sponsorMarketValueField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Market value must be a number.");
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "SQL Error: " + "Market value must be positive");
            }
        });

    }
}

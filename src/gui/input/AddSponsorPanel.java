package gui.input;

import db_methods.SponsorDbMethods;
import models.Sponsor;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddSponsorPanel extends JPanel {
    private final SponsorDbMethods sponsorService = new SponsorDbMethods();
    private final JTextField sponsorNameField;
    private final JTextField sponsorMarketValueField;
    private final JButton submitButton;
    private Sponsor editingSponsor = null;
    private MainFrame mainFrame;

    public AddSponsorPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Sponsor Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Sponsor Name:"), gbc);
        gbc.gridx = 1;
        sponsorNameField = new JTextField(20);
        add(sponsorNameField, gbc);

        // Market Value
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Market Value:"), gbc);
        gbc.gridx = 1;
        sponsorMarketValueField = new JTextField(20);
        add(sponsorMarketValueField, gbc);

        // Submit Button
        gbc.gridx = 1; gbc.gridy = 2;
        submitButton = new JButton("Add Sponsor");
        add(submitButton, gbc);

        // Submit logic
        submitButton.addActionListener(e -> handleSubmit());
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    private void handleSubmit() {
        String name = sponsorNameField.getText().trim();
        String valueText = sponsorMarketValueField.getText().trim();

        if (name.isEmpty() || valueText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            long marketValue = Long.parseLong(valueText);
            if (marketValue < 0) throw new NumberFormatException();

            if (editingSponsor == null) {
                Sponsor newSponsor = new Sponsor(name, marketValue);
                sponsorService.insertSponsor(newSponsor);
                JOptionPane.showMessageDialog(this, "Sponsor added!");
                services.CSV_Service.logAction("INSERT", "SPONSOR");
            } else {
                editingSponsor.setSponsorName(name);
                editingSponsor.setMarketValue(marketValue);
                sponsorService.updateSponsor(editingSponsor);
                JOptionPane.showMessageDialog(this, "Sponsor updated!");
                services.CSV_Service.logAction("UPDATE", "SPONSOR");
                editingSponsor = null;
                submitButton.setText("Add Sponsor");
            }

            sponsorNameField.setText("");
            sponsorMarketValueField.setText("");
            if (mainFrame != null) {
                mainFrame.getSponsorsPanel().loadSponsorsIntoList();
                mainFrame.showPanel("sponsors");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Market value must be a positive number.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public void loadSponsorForUpdate(Sponsor sponsor) {
        this.editingSponsor = sponsor;
        sponsorNameField.setText(sponsor.getSponsorName());
        sponsorMarketValueField.setText(String.valueOf(sponsor.getMarketValue()));
        submitButton.setText("Update Sponsor");

        if (mainFrame != null) {
            mainFrame.showPanel("addSponsor");
        }
    }
}

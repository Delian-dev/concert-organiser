package gui.panels;

import gui.MainFrame;
import models.Sponsor;
import db_methods.SponsorDbMethods;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class SponsorDetailsPanel extends JPanel {
    private final JLabel nameLabel;
    private final JLabel valueLabel;
    private final JButton updateButton;
    private final JButton deleteButton;
    private final MainFrame mainFrame;
    private Sponsor sponsor;

    private final SponsorDbMethods sponsorService = new SponsorDbMethods();

    public SponsorDetailsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel = new JLabel();
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(nameLabel, gbc);

        gbc.gridy++;
        add(valueLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        add(updateButton, gbc);

        gbc.gridx = 1;
        add(deleteButton, gbc);

        // Event handlers
        updateButton.addActionListener(e -> mainFrame.getAddSponsorPanel().loadSponsorForUpdate(sponsor));
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this sponsor?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    sponsorService.deleteSponsor(sponsor.getSponsorId());
                    JOptionPane.showMessageDialog(this, "Sponsor deleted.");
                    mainFrame.getSponsorsPanel().loadSponsorsIntoList();
                    mainFrame.showPanel("sponsors");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting sponsor: " + ex.getMessage());
                }
            }
        });
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
        nameLabel.setText("📛 Name: " + sponsor.getSponsorName());
        valueLabel.setText("💰 Market Value: $" + sponsor.getMarketValue());
    }
}

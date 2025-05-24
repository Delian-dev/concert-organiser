package gui.input;

import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import models.Country;
import models.Location;
import db_methods.LocationDbMethods;
import db_methods.CountryDbMethods;

public class AddLocationPanel extends JPanel {
    private final CountryDbMethods countryService = new CountryDbMethods();
    private final LocationDbMethods locationService = new LocationDbMethods();

    public AddLocationPanel(MainFrame mainFrame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField locationNameField = new JTextField(15);
        JTextField addressField = new JTextField(15);
        JComboBox<Country> countryCombo = new JComboBox<>(new Vector<>(countryService.selectAll()));

        JButton submitBtn = new JButton("Add Location");
        submitBtn.addActionListener(e -> {
            String city = locationNameField.getText();
            String address = addressField.getText();
            Country selected = (Country) countryCombo.getSelectedItem();

            if (city.isEmpty() || address.isEmpty() || selected == null) {
                JOptionPane.showMessageDialog(this, "Fill all fields!");
                return;
            }

            locationService.insertLocation(new Location(selected.getCountryId(), city,address));
            JOptionPane.showMessageDialog(this, "Location added!");
            services.CSV_Service.logAction("INSERT", "LOCATION");
            locationNameField.setText("");
        });

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Location Name:"), gbc);
        gbc.gridx = 1; add(locationNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Country:"), gbc);
        gbc.gridx = 1; add(countryCombo, gbc);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; add(submitBtn, gbc);
    }
}

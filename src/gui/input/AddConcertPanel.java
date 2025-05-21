package gui.input;

import java.sql.SQLException;
import java.util.List;

import db_methods.CountryDbMethods;
import db_methods.LocationDbMethods;
import models.Concert;
import models.Location;
import models.Country;
import validations.DateValidator;
import db_methods.ConcertDbMethods;
import exceptions.InvalidDateException;

import javax.swing.*;
import java.awt.*;

public class AddConcertPanel extends JPanel {
    private final JComboBox<Country> countryDropdown;
    private final JComboBox<Location> locationDropdown;
    private final JTextField concertNameField;
    private final JTextField concertDateField;
    private final JTextField concertCapacityField;
    private final JLabel messageLabel;
    private final JButton submitButton;

    private final ConcertDbMethods concertService = new ConcertDbMethods();
    private final CountryDbMethods countryService = new CountryDbMethods();
    private final LocationDbMethods locationService = new LocationDbMethods();

    private Concert concertToUpdate = null;

    public AddConcertPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Country dropdown
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Country:"), gbc);
        gbc.gridx = 1;
        countryDropdown = new JComboBox<>();
        add(countryDropdown, gbc);

        // Location dropdown
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        locationDropdown = new JComboBox<>();
        add(locationDropdown, gbc);

        // Message for no locations
        gbc.gridx = 1; gbc.gridy = 2;
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        add(messageLabel, gbc);

        // Concert Name
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Concert Name:"), gbc);
        gbc.gridx = 1;
        concertNameField = new JTextField(20);
        add(concertNameField, gbc);

        // Concert Date
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        concertDateField = new JTextField(20);
        add(concertDateField, gbc);

        // Capacity
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Capacity:"), gbc);
        gbc.gridx = 1;
        concertCapacityField = new JTextField(20);
        add(concertCapacityField, gbc);

        // Submit button
        gbc.gridx = 1; gbc.gridy = 6;
        submitButton = new JButton("Add Concert");
        add(submitButton, gbc);

        // Submit handler
        submitButton.addActionListener(e -> handleSubmit());

        loadCountries();
        addCountryListener();
    }

    private void loadCountries() {
        List<Country> countries = countryService.selectAll();
        for (Country c : countries) {
            countryDropdown.addItem(c);
        }
    }

    private void addCountryListener() {
        countryDropdown.addActionListener(e -> {
            Country selectedCountry = (Country) countryDropdown.getSelectedItem();
            locationDropdown.removeAllItems();
            messageLabel.setText("");

            if (selectedCountry != null) {
                List<Location> locations = locationService.selectLocationByCountry(selectedCountry.getCountryId());
                if (locations.isEmpty()) {
                    messageLabel.setText("No locations available for selected country.");
                } else {
                    for (Location loc : locations) {
                        locationDropdown.addItem(loc);
                    }
                }
            }
        });
    }

    private void handleSubmit() {
        try {
            Location selectedLocation = (Location) locationDropdown.getSelectedItem();
            if (selectedLocation == null) {
                JOptionPane.showMessageDialog(this, "Please select a location.");
                return;
            }

            String name = concertNameField.getText();
            String date = concertDateField.getText();
            int capacity = Integer.parseInt(concertCapacityField.getText());

            DateValidator validator = new DateValidator();
            if (!validator.isValid(date)) {
                throw new InvalidDateException("Invalid date format: " + date);
            }

            if (concertToUpdate == null) {
                // Insert new concert
                Concert concert = new Concert(selectedLocation.getLocationId(), name, date, capacity);
                concertService.insertConcert(concert);
                JOptionPane.showMessageDialog(this, "Concert added successfully!");
            } else {
                // Update existing concert
                concertToUpdate.setConcertName(name);
                concertToUpdate.setDate(date);
                concertToUpdate.setCapacity(capacity);
                concertToUpdate.setLocationId(selectedLocation.getLocationId());

                concertService.updateConcert(concertToUpdate);
                JOptionPane.showMessageDialog(this, "Concert updated successfully!");

                concertToUpdate = null;
                submitButton.setText("Add Concert");
            }

            // Clear form
            concertNameField.setText("");
            concertDateField.setText("");
            concertCapacityField.setText("");
            locationDropdown.setSelectedIndex(-1);
            messageLabel.setText("");

        } catch (InvalidDateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid capacity number.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
        }
    }

    public void loadConcertForUpdate(Concert concert) {
        this.concertToUpdate = concert;

        concertNameField.setText(concert.getConcertName());
        concertDateField.setText(concert.getDate());
        concertCapacityField.setText(String.valueOf(concert.getCapacity()));
        submitButton.setText("Update Concert");

//        // Tell user to manually select country
//        messageLabel.setText("Please select the concert's country to load the location.");
//
//        // Try to pre-select location (if available)
//        for (int i = 0; i < locationDropdown.getItemCount(); i++) {
//            Location loc = locationDropdown.getItemAt(i);
//            if (loc.getLocationId().equals(concert.getLocationId())) {
//                locationDropdown.setSelectedItem(loc);
//                break;
//            }
//        }
    }
}

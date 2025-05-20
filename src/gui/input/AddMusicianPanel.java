package gui.input;

import exceptions.InvalidDateException;
import models.Band;
import models.SoloArtist;
import db_methods.SoloArtistDbMethods;
import db_methods.BandDbMethods;
import validations.DateValidator;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddMusicianPanel extends JPanel {
    private final JComboBox<String> typeCombo;
    private final JTextField nameField = new JTextField(15);
    private final JTextField genreField = new JTextField(15);

    // SoloArtist fields
    private final JTextField birthdateField = new JTextField(15);
    private final JTextField instrumentField = new JTextField(15);

    // Band field
    private final JTextField dateFormedField = new JTextField(15);

    private final JPanel dynamicFieldsPanel = new JPanel(new GridBagLayout());

    private final SoloArtistDbMethods soloArtistService = new SoloArtistDbMethods();
    private final BandDbMethods bandService = new BandDbMethods();

    public AddMusicianPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);

        typeCombo = new JComboBox<>(new String[]{"Solo Artist", "Band"});
        typeCombo.addActionListener(e -> updateDynamicFields());

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Musician Type:"), gbc);
        gbc.gridx = 1; add(typeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1; add(genreField, gbc);

        // Dynamic panel: contains solo/band fields
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(dynamicFieldsPanel, gbc);

        updateDynamicFields(); // show initial fields

        JButton submitButton = new JButton("Add Musician");
        submitButton.addActionListener(e -> handleSubmit());

        gbc.gridy = 4;
        add(submitButton, gbc);
    }

    private void updateDynamicFields() {
        dynamicFieldsPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        if ("Solo Artist".equals(typeCombo.getSelectedItem())) {
            gbc.gridx = 0; gbc.gridy = 0; dynamicFieldsPanel.add(new JLabel("Birthdate (YYYY-MM-DD):"), gbc);
            gbc.gridx = 1; dynamicFieldsPanel.add(birthdateField, gbc);

            gbc.gridx = 0; gbc.gridy = 1; dynamicFieldsPanel.add(new JLabel("Instrument:"), gbc);
            gbc.gridx = 1; dynamicFieldsPanel.add(instrumentField, gbc);
        } else {
            gbc.gridx = 0; gbc.gridy = 0; dynamicFieldsPanel.add(new JLabel("Date (YYYY-MM-DD) Formed:"), gbc);
            gbc.gridx = 1; dynamicFieldsPanel.add(dateFormedField, gbc);
        }

        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void handleSubmit() {
        String name = nameField.getText().trim();
        String genre = genreField.getText().trim();

        if (name.isEmpty() || genre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and genre are required.");
            return;
        }

        try {
            if ("Solo Artist".equals(typeCombo.getSelectedItem())) {
                String birthdate = birthdateField.getText().trim();
                String instrument = instrumentField.getText().trim();

                if (birthdate.isEmpty() || instrument.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Birthdate and instrument are required for a solo artist.");
                    return;
                }

                DateValidator dateValidator = new DateValidator();
                if(!dateValidator.isValid(birthdate)) {
                    throw new InvalidDateException("Invalid birthdate format: " + birthdate);
                }

                soloArtistService.insertSoloArtist(new SoloArtist(name, genre, birthdate, instrument));
            } else {
                String dateFormed = dateFormedField.getText().trim();

                if (dateFormed.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Date formed is required for a band.");
                    return;
                }

                DateValidator dateValidator = new DateValidator();
                if(!dateValidator.isValid(dateFormed)) {
                    throw new InvalidDateException("Invalid date format: " + dateFormed);
                }
                bandService.insertBand(new Band(name, genre, dateFormed));
            }

            JOptionPane.showMessageDialog(this, "Musician added successfully!");
            clearFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
        catch(InvalidDateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        genreField.setText("");
        birthdateField.setText("");
        instrumentField.setText("");
        dateFormedField.setText("");
    }
}

package gui.input;

import db_methods.BandDbMethods;
import db_methods.SoloArtistDbMethods;
import gui.MainFrame;
import models.Band;
import models.SoloArtist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddMusicianPanel extends JPanel {
    private final JTextField nameTextField;
    private final JTextField genreTextField;
    private final JTextField extraInfoTextField;
    private final JTextField dateTextField;
    private final JButton submitButton;
    private final JButton backButton;

    private SoloArtist editingSoloArtist;
    private Band editingBand;

    private final MainFrame mainFrame;

    public AddMusicianPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());

        // Outer wrapper for centering
        JPanel outerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Inner form panel with labels and text fields
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameTextField = new JTextField(20);
        genreTextField = new JTextField(20);
        dateTextField = new JTextField(20);
        extraInfoTextField = new JTextField(20);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameTextField);

        formPanel.add(new JLabel("Genre:"));
        formPanel.add(genreTextField);

        formPanel.add(new JLabel("Date (Birthdate or Formed):"));
        formPanel.add(dateTextField);

        formPanel.add(new JLabel("Extra Info (Instrument or empty):"));
        formPanel.add(extraInfoTextField);

        outerPanel.add(formPanel, gbc);
        add(outerPanel, BorderLayout.CENTER);

        // Buttons at bottom
        JPanel buttonsPanel = new JPanel();
        submitButton = new JButton("Add Musician");
        backButton = new JButton("Back");
        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(new SubmitListener());
        backButton.addActionListener(e -> mainFrame.showPanel("musicians"));
    }

    public void loadSoloArtistForUpdate(SoloArtist soloArtist) {
        editingSoloArtist = soloArtist;
        editingBand = null;

        nameTextField.setText(soloArtist.getName());
        genreTextField.setText(soloArtist.getGenre());
        dateTextField.setText(soloArtist.getBirthdate());
        extraInfoTextField.setText(soloArtist.getInstrument());
        submitButton.setText("Update Solo Artist");
    }

    public void loadBandForUpdate(Band band) {
        editingBand = band;
        editingSoloArtist = null;

        nameTextField.setText(band.getName());
        genreTextField.setText(band.getGenre());
        dateTextField.setText(band.getDateFormed());
        extraInfoTextField.setText("");
        submitButton.setText("Update Band");
    }

    private class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameTextField.getText().trim();
            String genre = genreTextField.getText().trim();
            String date = dateTextField.getText().trim();
            String extraInfo = extraInfoTextField.getText().trim();

            if (name.isEmpty() || genre.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(AddMusicianPanel.this,
                        "Name, Genre, and Date fields cannot be empty.");
                return;
            }

            SoloArtistDbMethods soloArtistDbMethods = new SoloArtistDbMethods();
            BandDbMethods bandDbMethods = new BandDbMethods();

            try {
                if (editingSoloArtist != null) {
                    // Update solo artist
                    editingSoloArtist.setName(name);
                    editingSoloArtist.setGenre(genre);
                    editingSoloArtist.setBirthdate(date);
                    editingSoloArtist.setInstrument(extraInfo);

                    soloArtistDbMethods.updateSoloArtist(editingSoloArtist);
                    JOptionPane.showMessageDialog(AddMusicianPanel.this,
                            "Solo artist updated successfully!");
                    services.CSV_Service.logAction("UPDATE", "MUSICIAN(SOLO ARTIST)");
                } else if (editingBand != null) {
                    // Update band
                    editingBand.setName(name);
                    editingBand.setGenre(genre);
                    editingBand.setDateFormed(date);

                    bandDbMethods.updateBand(editingBand);
                    JOptionPane.showMessageDialog(AddMusicianPanel.this,
                            "Band updated successfully!");
                    services.CSV_Service.logAction("UPDATE", "MUSICIAN(BAND)");
                } else {
                    // Add new: check if extraInfo filled to distinguish solo or band
                    if (!extraInfo.isEmpty()) {
                        // Add new solo artist
                        SoloArtist newSolo = new SoloArtist(0, name, genre, date, extraInfo);
                        soloArtistDbMethods.insertSoloArtist(newSolo);
                        JOptionPane.showMessageDialog(AddMusicianPanel.this,
                                "New solo artist added!");
                        services.CSV_Service.logAction("INSERT", "MUSICIAN(SOLO ARTIST)");
                    } else {
                        // Add new band
                        Band newBand = new Band(0, name, genre, date);
                        bandDbMethods.insertBand(newBand);
                        JOptionPane.showMessageDialog(AddMusicianPanel.this,
                                "New band added!");
                        services.CSV_Service.logAction("INSERT", "MUSICIAN(BAND)");
                    }
                }

                clearFields();
                mainFrame.getMusiciansPanel().loadMusiciansIntoList();
                mainFrame.showPanel("musicians");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(AddMusicianPanel.this,
                        "Database error: " + ex.getMessage());
            }
        }
    }

    private void clearFields() {
        nameTextField.setText("");
        genreTextField.setText("");
        dateTextField.setText("");
        extraInfoTextField.setText("");
        submitButton.setText("Add Musician");
        editingSoloArtist = null;
        editingBand = null;
    }
}

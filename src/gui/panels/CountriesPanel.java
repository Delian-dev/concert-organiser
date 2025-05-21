package gui.panels;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

import gui.MainFrame;
import models.Country;
import models.Location;
import db_methods.CountryDbMethods;
import db_methods.LocationDbMethods;


public class CountriesPanel extends JPanel {
    private final CountryDbMethods countryService = new CountryDbMethods();
    private final LocationDbMethods locationService = new LocationDbMethods();

    public CountriesPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("🌍 Countries & Locations", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        add(header, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane, BorderLayout.CENTER);

        List<Country> countries = countryService.selectAll();
        for (Country country : countries) {
            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new BoxLayout(countryPanel, BoxLayout.Y_AXIS));
            countryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            countryPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(""),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            JButton expandButton = new JButton("▼");
            expandButton.setPreferredSize(new Dimension(40, 30));

            JLabel countryLabel = new JLabel(country.getCountry_name(), SwingConstants.CENTER);
            countryLabel.setFont(new Font("Arial", Font.BOLD, 16));

            headerPanel.add(expandButton, BorderLayout.WEST);
            headerPanel.add(countryLabel, BorderLayout.CENTER);

            JPanel locationsPanel = new JPanel();
            locationsPanel.setLayout(new BoxLayout(locationsPanel, BoxLayout.Y_AXIS));
            locationsPanel.setVisible(false);

            countryPanel.add(headerPanel);
            countryPanel.add(locationsPanel);

            expandButton.addActionListener(e -> {
                if (locationsPanel.isVisible()) {
                    locationsPanel.setVisible(false);
                    expandButton.setText("▶");
                } else {
                    List<Location> locations = locationService.selectLocationByCountry(country.getCountryId());
                    locationsPanel.removeAll();
                    if (locations.isEmpty()) {
                        locationsPanel.add(new JLabel("No locations found."));
                    } else {
                        for (Location loc : locations) {
                            JPanel locationRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0)); // spacing: 8px horizontally
                            locationRow.setAlignmentX(Component.LEFT_ALIGNMENT);

                            JLabel locationLabel = new JLabel("📍 " + loc.getCity() + " - " + loc.getAddress());

                            JButton deleteBtn = new JButton("X");
                            deleteBtn.setPreferredSize(new Dimension(20, 20));
                            deleteBtn.setFont(new Font("Arial", Font.BOLD, 10));
                            deleteBtn.setForeground(Color.RED);
                            deleteBtn.setFocusPainted(false);
                            deleteBtn.setBorder(BorderFactory.createEmptyBorder());
                            deleteBtn.setContentAreaFilled(false);
                            deleteBtn.setToolTipText("Delete this location");

                            deleteBtn.addActionListener(ev -> {
                                int confirm = JOptionPane.showConfirmDialog(
                                        this,
                                        "Are you sure you want to delete this location?",
                                        "Confirm Delete",
                                        JOptionPane.YES_NO_OPTION
                                );
                                if (confirm == JOptionPane.YES_OPTION) {
                                    locationService.deleteLocation(loc.getLocationId());
                                    locationsPanel.remove(locationRow);
                                    locationsPanel.revalidate();
                                    locationsPanel.repaint();
                                }
                            });

                            locationRow.add(locationLabel);
                            locationRow.add(deleteBtn);
                            locationsPanel.add(locationRow);
                        }

                    }
                    locationsPanel.setVisible(true);
                    expandButton.setText("▼");
                    locationsPanel.revalidate();
                }
            });

            countryPanel.add(expandButton, BorderLayout.WEST);
            countryPanel.add(locationsPanel, BorderLayout.SOUTH);
            listPanel.add(countryPanel);
            listPanel.add(Box.createVerticalStrut(20));
        }

        JButton addLocationBtn = new JButton("Add Location");
        int width = 120;
        int height = 30;
        Dimension fixedSize = new Dimension(width, height);
        addLocationBtn.setPreferredSize(fixedSize);
        addLocationBtn.setMaximumSize(fixedSize);
        addLocationBtn.setMinimumSize(fixedSize);
        addLocationBtn.addActionListener(e -> mainFrame.showPanel("locationForm")); // Switch to a form panel

        JPanel locationButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        locationButtonPanel.add(addLocationBtn);

        add(locationButtonPanel, BorderLayout.SOUTH);
    }
}

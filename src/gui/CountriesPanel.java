package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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
            JPanel countryPanel = new JPanel(new BorderLayout());
            countryPanel.setBorder(BorderFactory.createTitledBorder(country.getCountry_name()));

            JButton expandButton = new JButton("▶");
            JPanel locationsPanel = new JPanel();
            locationsPanel.setLayout(new BoxLayout(locationsPanel, BoxLayout.Y_AXIS));
            locationsPanel.setVisible(false); // collapsed by default

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
                            locationsPanel.add(new JLabel("📍 " + loc.getCity() + "- " + loc.getAddress()));
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
        }

        JButton addLocationBtn = new JButton("➕ Add Location");
        addLocationBtn.addActionListener(e -> mainFrame.showPanel("locationForm")); // Switch to a form panel
        add(addLocationBtn, BorderLayout.SOUTH);
    }
}

package gui.panels;

import db_methods.BandDbMethods;
import db_methods.SoloArtistDbMethods;
import gui.MainFrame;
import models.Band;
import models.Musician;
import models.SoloArtist;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicianDetailsPanel extends JPanel {
    private final JPanel contentPanel;
    private Musician currentMusician;
    private Runnable onUpdateRequested;
    private Runnable onDeleted;

    private final JButton updateButton;
    private final JButton deleteButton;

    private final SoloArtistDbMethods soloArtistService = new SoloArtistDbMethods();
    private final BandDbMethods bandService = new BandDbMethods();

    private final List<JLabel> infoLabels = new ArrayList<>();

    public MusicianDetailsPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Musician Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            if (currentMusician != null && onUpdateRequested != null) {
                onUpdateRequested.run();
            }
        });

        deleteButton.addActionListener(e -> {
            if (currentMusician == null) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this musician?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (currentMusician instanceof SoloArtist) {
                        soloArtistService.deleteSoloArtist(currentMusician.getMusicianId());
                    } else if (currentMusician instanceof Band) {
                        bandService.deleteBand(currentMusician.getMusicianId());
                    }
                    JOptionPane.showMessageDialog(this, "Musician deleted successfully.");
                    if (onDeleted != null) onDeleted.run();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Delete failed: " + ex.getMessage());
                }
            }
        });
    }

    public void setMusician(Musician musician, Runnable onUpdateRequested, Runnable onDeleted) {
        this.currentMusician = musician;
        this.onUpdateRequested = onUpdateRequested;
        this.onDeleted = onDeleted;

        contentPanel.removeAll();
        infoLabels.clear();

        if (musician == null) {
            contentPanel.add(new JLabel("No musician selected."));
        } else {
            addLabel("Name: " + musician.getName());
            addLabel("Genre: " + musician.getGenre());

            if (musician instanceof SoloArtist sa) {
                addLabel("Type: Solo Artist");
                addLabel("Birthdate: " + sa.getBirthdate());
                addLabel("Instrument: " + sa.getInstrument());
            } else if (musician instanceof Band band) {
                addLabel("Type: Band");
                addLabel("Date Formed: " + band.getDateFormed());
            }
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabels.add(label);
        contentPanel.add(label);
    }
}

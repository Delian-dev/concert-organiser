package gui.panels;

import db_methods.BandDbMethods;
import db_methods.SoloArtistDbMethods;
import gui.MainFrame;
import models.Band;
import models.Concert;
import models.Musician;
import models.SoloArtist;
import services.MusicianService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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

    private final SoloArtistDbMethods soloArtistService = SoloArtistDbMethods.getInstance();
    private final BandDbMethods bandService = BandDbMethods.getInstance();
    private final MusicianService musicianService = new MusicianService();

    private final List<JLabel> infoLabels = new ArrayList<>();

    public MusicianDetailsPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("🎼 Musician Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 28));
        add(headerLabel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        updateButton = new JButton("✏ Update");
        deleteButton = new JButton("🗑 Delete");
        JButton backButton = new JButton("⬅ Back");
        backButton.addActionListener(e -> mainFrame.showPanel("musicians"));
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            if (currentMusician != null && onUpdateRequested != null) {
                onUpdateRequested.run(); //called in mainframe
            }
        });

        deleteButton.addActionListener(e -> {
            if (currentMusician == null) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this musician?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

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
            JLabel noMusicianLabel = new JLabel("No musician selected.");
            noMusicianLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            contentPanel.add(noMusicianLabel);
        } else {
            addLabel("👤 Name: " + musician.getName());
            addLabel("🎶 Genre: " + musician.getGenre());

            if (musician instanceof SoloArtist sa) {
                addLabel("🎤 Type: Solo Artist");
                addLabel("🎂 Birthdate: " + sa.getBirthdate());
                addLabel("🎸 Instrument: " + sa.getInstrument());
            } else if (musician instanceof Band band) {
                addLabel("👥 Type: Band");
                addLabel("📅 Date Formed: " + band.getDateFormed());
            }

            contentPanel.add(Box.createVerticalStrut(20));
            contentPanel.add(createConcertsSection(musician.getMusicianId()));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabels.add(label);
        contentPanel.add(label);
        contentPanel.add(Box.createVerticalStrut(6));
    }

    private JPanel createConcertsSection(int musicianId) {
        List<Concert> concerts = musicianService.getConcertsByMusicianId(musicianId);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "🎫 Associated Concerts",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI Emoji", Font.BOLD, 18),
                Color.DARK_GRAY
        ));

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 16);

        if (concerts.isEmpty()) {
            JLabel label = new JLabel("<html><i>No concerts for this musician yet</i></html>");
            panel.add(label);
        } else {
            for (Concert concert : concerts) {
                JLabel label = new JLabel("🎤 " + concert.getConcertName() + " — " + concert.getDate());
                label.setFont(font);
                label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                panel.add(label);
            }
        }

        return panel;
    }
}

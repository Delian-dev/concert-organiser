package gui.panels;

import db_methods.BandDbMethods;
import db_methods.SoloArtistDbMethods;
import gui.MainFrame;
import gui.components.RoundedBorder;
import gui.components.RoundedPanel;
import models.Band;
import models.Musician;
import models.SoloArtist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MusiciansPanel extends JPanel {
    private final JPanel musiciansListPanel;
    private final MainFrame mainFrame;

    public MusiciansPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel header = new JLabel("🎵 Musicians", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        add(header, BorderLayout.NORTH);

        musiciansListPanel = new JPanel();
        musiciansListPanel.setLayout(new BoxLayout(musiciansListPanel, BoxLayout.Y_AXIS));
        musiciansListPanel.setBackground(Color.WHITE);
        musiciansListPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JScrollPane scrollPane = new JScrollPane(musiciansListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton loadMusiciansBtn = new JButton("Load Musicians");
        JButton addMusicianBtn = new JButton("Add Musician");

        loadMusiciansBtn.addActionListener(e -> loadMusiciansIntoList());
        addMusicianBtn.addActionListener(e -> mainFrame.showPanel("addMusician"));

        buttonsPanel.add(loadMusiciansBtn);
        buttonsPanel.add(addMusicianBtn);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void loadMusiciansIntoList() {
        SoloArtistDbMethods soloService = SoloArtistDbMethods.getInstance();
        BandDbMethods bandService = BandDbMethods.getInstance();

        List<SoloArtist> soloArtists = soloService.selectAll();
        List<Band> bands = bandService.selectAll();

        musiciansListPanel.removeAll();

        for (SoloArtist s : soloArtists) {
            musiciansListPanel.add(createSoloArtistCard(s));
            musiciansListPanel.add(Box.createVerticalStrut(15));
        }

        for (Band b : bands) {
            musiciansListPanel.add(createBandCard(b));
            musiciansListPanel.add(Box.createVerticalStrut(15));
        }

        musiciansListPanel.revalidate();
        musiciansListPanel.repaint();
    }

    private JPanel createSoloArtistCard(SoloArtist artist) {
        JPanel panel = createMusicianCard(
                "Solo Artist",
                artist.getName(),
                artist.getGenre(),
                "Instrument: " + artist.getInstrument() + " | Birthdate: " + artist.getBirthdate()
        );

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(173, 216, 230));
                panel.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(15, 3, Color.BLUE),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.LIGHT_GRAY);
                panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showMusicianDetails(artist);
            }
        });

        return panel;
    }

    private JPanel createBandCard(Band band) {
        JPanel panel = createMusicianCard(
                "Band",
                band.getName(),
                band.getGenre(),
                "Formed: " + band.getDateFormed()
        );

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(173, 216, 230));
                panel.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(15, 3, Color.BLUE),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.LIGHT_GRAY);
                panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showMusicianDetails(band);
            }
        });

        return panel;
    }

    private JPanel createMusicianCard(String type, String name, String genre, String extraInfo) {
        JPanel panel = new RoundedPanel();
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(new Dimension(500, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel nameLabel = new JLabel("🎤 " + name + " (" + type + ")", SwingConstants.LEFT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel genreLabel = new JLabel("Genre: " + genre + " | " + extraInfo, SwingConstants.LEFT);
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(nameLabel);
        textPanel.add(genreLabel);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }
}

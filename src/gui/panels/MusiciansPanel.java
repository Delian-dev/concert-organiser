package gui.panels;

import db_methods.BandDbMethods;
import db_methods.SoloArtistDbMethods;
import gui.MainFrame;
import gui.components.RoundedBorder;
import gui.components.RoundedPanel;
import models.Band;
import models.Musician;
import models.SoloArtist;
import services.MusicianService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MusiciansPanel extends JPanel {
    private final JPanel musiciansListPanel;
    private final MainFrame mainFrame;

    private final JComboBox<String> genreFilterBox = new JComboBox<>();
    private final MusicianService musicianService = new MusicianService();

    public MusiciansPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel header = new JLabel("🎵 Musicians", SwingConstants.CENTER);
        header.setFont(new Font("SEGOE UI EMOJI", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
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

        genreFilterBox.addItem("All"); // default item
        musicianService.getUniqueGenres().forEach(genreFilterBox::addItem);
        buttonsPanel.add(new JLabel("Filter by Genre:"));
        buttonsPanel.add(genreFilterBox);

        genreFilterBox.addActionListener(e -> loadMusiciansByGenre());

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
        nameLabel.setFont(new Font("SEGOE UI EMOJI", Font.BOLD, 18));

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

    public void loadMusiciansByGenre() {
        String selectedGenre = (String) genreFilterBox.getSelectedItem();

        musiciansListPanel.removeAll();

        List<Musician> filteredMusicians;

        if (selectedGenre == null || selectedGenre.equals("All")) {
            filteredMusicians = musicianService.getAllMusicians();
        } else {
            filteredMusicians = musicianService.listMusiciansByGenre(selectedGenre);
            //System.out.println(selectedGenre);
        }

        for (Musician musician : filteredMusicians) {
            //System.out.println(musician);
            if (musician instanceof SoloArtist) {
                musiciansListPanel.add(createSoloArtistCard((SoloArtist) musician));
            } else if (musician instanceof Band) {
                musiciansListPanel.add(createBandCard((Band) musician));
            }
            musiciansListPanel.add(Box.createVerticalStrut(15));
        }

        services.CSV_Service.logAction("FILTER BY GENRE (" + selectedGenre + ")", "MUSICIAN");

        musiciansListPanel.revalidate();
        musiciansListPanel.repaint();
    }

    public void addNotify() {
        super.addNotify();
        loadMusiciansIntoList();
    }
}

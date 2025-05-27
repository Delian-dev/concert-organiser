package gui;

import gui.input.*;
import gui.panels.*;
import gui.associate.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final ConcertDetailsPanel concertDetailsPanel;
    private final AddConcertPanel addConcertPanel;
    private final ConcertsPanel concertsPanel;

    private final MusicianDetailsPanel musicianDetailsPanel;
    private final AddMusicianPanel addMusicianPanel;
    private final MusiciansPanel musiciansPanel;

    private final SponsorsPanel sponsorsPanel;
    private final AddSponsorPanel addSponsorPanel;
    private final SponsorDetailsPanel sponsorDetailsPanel;

    private final AddTicketPanel addTicketPanel;

    private final AssociateMCPanel associateMusicianConcertPanel;
    private final AssociateSCPanel associateSponsorConcertPanel;

    private final UpdateClient updateClientPanel;

    public MainFrame() {
        setTitle("Concert Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel(new GridLayout(0, 1));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton homeButton = new JButton("Home");
        JButton sponsorsBtn = new JButton("Sponsors");
        JButton concertsBtn = new JButton("Concerts");
        JButton musicianBtn = new JButton("Musicians");
        JButton clientsBtn = new JButton("Clients");
        JButton addSponsorBtn = new JButton("Add Sponsor");
        JButton addConcertBtn = new JButton("Add Concert");
        JButton addMusicianBtn = new JButton("Add Musician");
        JButton addClientBtn = new JButton("Add Client");
        JButton countriesBtn = new JButton("Countries/Locations");

        sidebar.add(homeButton);
        sidebar.add(musicianBtn);
        sidebar.add(sponsorsBtn);
        sidebar.add(concertsBtn);
        sidebar.add(clientsBtn);
        sidebar.add(countriesBtn);

        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new HomePanel(), "home");

        concertsPanel = new ConcertsPanel(this);
        contentPanel.add(concertsPanel, "concerts");

        concertDetailsPanel = new ConcertDetailsPanel(this);
        contentPanel.add(concertDetailsPanel, "concertDetailsForm");

        addConcertPanel = new AddConcertPanel();
        contentPanel.add(addConcertPanel, "addConcert");

        musiciansPanel = new MusiciansPanel(this);
        contentPanel.add(musiciansPanel, "musicians");

        addMusicianPanel = new AddMusicianPanel(this);
        contentPanel.add(addMusicianPanel, "addMusician");

        musicianDetailsPanel = new MusicianDetailsPanel(this);
        contentPanel.add(musicianDetailsPanel, "musicianDetailsForm");

        contentPanel.add(new ClientsPanel(this), "clients");
        contentPanel.add(new CountriesPanel(this), "countries");
        contentPanel.add(new AddClientPanel(), "addClient");
        contentPanel.add(new AddLocationPanel(this), "locationForm");

        sponsorsPanel = new SponsorsPanel(this);
        addSponsorPanel = new AddSponsorPanel();
        addSponsorPanel.setMainFrame(this);
        sponsorDetailsPanel = new SponsorDetailsPanel(this);

        addTicketPanel = new AddTicketPanel();
        contentPanel.add(addTicketPanel, "addTicket");

        contentPanel.add(sponsorsPanel, "sponsors");
        contentPanel.add(addSponsorPanel, "addSponsor");
        contentPanel.add(sponsorDetailsPanel, "sponsorDetails");

        associateMusicianConcertPanel = new AssociateMCPanel(this);
        contentPanel.add(associateMusicianConcertPanel, "associateMusician");

        associateSponsorConcertPanel = new AssociateSCPanel(this);
        contentPanel.add(associateSponsorConcertPanel, "associateSponsor");

        updateClientPanel = new UpdateClient();
        contentPanel.add(updateClientPanel, "updateClient");

        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "home");

        // button listeners
        homeButton.addActionListener(e -> cardLayout.show(contentPanel, "home"));
        musicianBtn.addActionListener(e -> cardLayout.show(contentPanel, "musicians"));
        sponsorsBtn.addActionListener(e -> cardLayout.show(contentPanel, "sponsors"));
        concertsBtn.addActionListener(e -> cardLayout.show(contentPanel, "concerts"));
        clientsBtn.addActionListener(e -> cardLayout.show(contentPanel, "clients"));
        addMusicianBtn.addActionListener(e -> cardLayout.show(contentPanel, "addMusician"));
        addConcertBtn.addActionListener(e -> cardLayout.show(contentPanel, "addConcert"));
        addSponsorBtn.addActionListener(e -> cardLayout.show(contentPanel, "addSponsor"));
        addClientBtn.addActionListener(e -> cardLayout.show(contentPanel, "addClient"));
        countriesBtn.addActionListener(e -> cardLayout.show(contentPanel, "countries"));

        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }

    public ConcertDetailsPanel getConcertDetailsPanel() {
        return concertDetailsPanel;
    }

    public AddConcertPanel getAddConcertPanel() {
        return addConcertPanel;
    }

    public MusiciansPanel getMusiciansPanel() {
        return musiciansPanel;
    }

    public void showMusicianDetails(models.Musician musician) {
        musicianDetailsPanel.setMusician(musician,
                () -> { // onUpdateRequested
                    if (musician instanceof models.SoloArtist) {
                        addMusicianPanel.loadSoloArtistForUpdate((models.SoloArtist) musician);
                    } else if (musician instanceof models.Band) {
                        addMusicianPanel.loadBandForUpdate((models.Band) musician);
                    }
                    showPanel("addMusician");
                },
                () -> { // onDeleted
                    showPanel("musicians");
                    musiciansPanel.loadMusiciansIntoList();
                }
        );
        showPanel("musicianDetailsForm");
    }

    public void showSponsorDetails(models.Sponsor sponsor) {
        sponsorDetailsPanel.setSponsor(
                sponsor,
                () -> {
                    addSponsorPanel.loadSponsorForUpdate(sponsor);
                    showPanel("addSponsor");
                },
                () -> {
                    sponsorsPanel.loadSponsorsIntoList();
                    showPanel("sponsors");
                }
        );
        showPanel("sponsorDetails");
    }


    public SponsorsPanel getSponsorsPanel() { return sponsorsPanel; }

    public AddTicketPanel getAddTicketPanel() {
        return addTicketPanel;
    }

    public AssociateMCPanel getAssociateMusicianConcertPanel() {
        return associateMusicianConcertPanel;
    }

    public AssociateSCPanel getAssociateSponsorConcertPanel() {
        return associateSponsorConcertPanel;
    }

    public UpdateClient getUpdateClientPanel() {
        return updateClientPanel;
    }
}

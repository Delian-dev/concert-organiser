package gui.associate;

import db_methods.SponsorConcertDbMethods;
import gui.MainFrame;
import models.Concert;
import models.Sponsor;
import models.SponsorConcert;
import models.SponsorType;
import services.SponsorService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssociateSCPanel extends JPanel {
    private final MainFrame mainFrame;

    private final JComboBox<Sponsor> sponsorComboBox = new JComboBox<>();
    private final JComboBox<SponsorType> sponsorTypeComboBox = new JComboBox<>(SponsorType.values());
    private Concert currentConcert;

    public AssociateSCPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("🤝 Associate Sponsor with Concert");
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        formPanel.add(new JLabel("🏢 Sponsor:"));
        formPanel.add(sponsorComboBox);

        formPanel.add(new JLabel("💎 Sponsor Type:"));
        formPanel.add(sponsorTypeComboBox);

        JButton associateBtn = new JButton("✅ Associate");
        associateBtn.addActionListener(e -> associateSponsor());

        JButton backBtn = new JButton("⬅ Back");
        backBtn.addActionListener(e -> mainFrame.showPanel("concertDetailsForm"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(associateBtn);
        buttonPanel.add(backBtn);

        // center formPanel both horizontally and vertically
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerWrapper.add(formPanel, gbc);

        add(centerWrapper, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setConcert(Concert concert) {
        this.currentConcert = concert;
        loadSponsors();
        sponsorTypeComboBox.setSelectedIndex(0);
    }

    private void loadSponsors() {
        sponsorComboBox.removeAllItems();
        SponsorService sponsorService = new SponsorService();
        List<Sponsor> sponsors = sponsorService.getAllSponsors();
        for (Sponsor s : sponsors) {
            sponsorComboBox.addItem(s);
        }
    }

    private void associateSponsor() {
        Sponsor selectedSponsor = (Sponsor) sponsorComboBox.getSelectedItem();
        SponsorType selectedType = (SponsorType) sponsorTypeComboBox.getSelectedItem();

        if (selectedSponsor == null || selectedType == null || currentConcert == null) {
            JOptionPane.showMessageDialog(this, "Please select a sponsor and ensure a concert is loaded.");
            return;
        }

        SponsorConcert sc = new SponsorConcert(
                currentConcert.getConcertId(),
                selectedSponsor.getSponsorId(),
                selectedType
        );

        try {
            SponsorConcertDbMethods.getInstance().insertSponsorConcert(sc);
            JOptionPane.showMessageDialog(this, "Sponsor successfully associated!");
            services.CSV_Service.logAction("ASSOCIATE", "SPONSOR-CONCERT");
            mainFrame.getConcertDetailsPanel().setConcert(currentConcert);
            mainFrame.showPanel("concertDetailsForm");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

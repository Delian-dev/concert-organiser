package gui.associate;

import db_methods.MusicianConcertDbMethods;
import models.Concert;
import models.Musician;
import services.ConcertService;
import services.MusicianService;
import models.MusicianConcert;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssociateMCPanel extends JPanel {
    private final MainFrame mainFrame;

    private final JComboBox<Musician> musicianComboBox = new JComboBox<>();
    private final JTextField feeField = new JTextField(10);
    private final JTextField durationField = new JTextField(10);
    private Concert currentConcert;

    public AssociateMCPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("🎤 Associate Musician with Concert");
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("🎸 Musician:"));
        JPanel musicianPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        musicianComboBox.setPreferredSize(new Dimension(150, 25));
        musicianPanel.add(musicianComboBox);
        formPanel.add(musicianPanel);

        formPanel.add(new JLabel("💰 Musician Fee:"));
        JPanel feePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        feeField.setPreferredSize(new Dimension(100, 25));
        feePanel.add(feeField);
        formPanel.add(feePanel);

        formPanel.add(new JLabel("⏱ Performance Duration (min):"));
        JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        durationField.setPreferredSize(new Dimension(100, 25));
        durationPanel.add(durationField);
        formPanel.add(durationPanel);

        JButton associateBtn = new JButton("✅ Associate");
        associateBtn.addActionListener(e -> associateMusician());

        JButton backBtn = new JButton("⬅ Back");
        backBtn.addActionListener(e -> mainFrame.showPanel("concertDetailsForm"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(associateBtn);
        buttonPanel.add(backBtn);

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
        loadMusicians();
        clearForm();
    }

    private void loadMusicians() {
        MusicianService musicianService = new MusicianService();
        List<Musician> musicians = musicianService.getAllMusicians();

        musicianComboBox.removeAllItems();
        for (Musician  m : musicians) {
            musicianComboBox.addItem(m);
        }
    }

    private void clearForm() {
        feeField.setText("");
        durationField.setText("");
    }

    private void associateMusician() {
        Musician selected = (Musician) musicianComboBox.getSelectedItem();
        if (selected == null || currentConcert == null) {
            JOptionPane.showMessageDialog(this, "Please select a musician and ensure a concert is loaded.");
            return;
        }

        try {
            int fee = Integer.parseInt(feeField.getText());
            int duration = Integer.parseInt(durationField.getText());

            MusicianConcert mc = new MusicianConcert(
                    currentConcert.getConcertId(),
                    selected.getMusicianId(),
                    fee,
                    duration
            );
//            System.out.println(currentConcert.getConcertId());
//            System.out.println(selected.getMusicianId() + " " + selected.getName());
//            System.out.println(fee);
//            System.out.println(duration);
            MusicianConcertDbMethods db = new MusicianConcertDbMethods();
            db.insertMusicianConcert(mc);
            JOptionPane.showMessageDialog(this, "Musician successfully associated!");
            mainFrame.getConcertDetailsPanel().setConcert(currentConcert);
            mainFrame.showPanel("concertDetailsForm");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for fee and duration.");
        }
    }
}

package gui.panels;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🎶 Concert Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        JTextPane description = new JTextPane();
        description.setText("""
                Welcome to the Concert Management App!
                
                From here you can:
                - View and manage Concerts
                - View and manage Musicians
                - View and manage Sponsors
                - Associate Musicians and Sponsors to Concerts
                - View Ticket Holders and Tickets data
                - Add new concerts or locations
                - Explore countries and their event venues
                
                Use the navigation on the left to get started.""");
        description.setEditable(false);

        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setBackground(getBackground());

        // using StyledDocument in order to center the text
        StyledDocument doc = description.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceAbove(center, 10);
        StyleConstants.setSpaceBelow(center, 10);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        add(description, BorderLayout.CENTER);
    }
}

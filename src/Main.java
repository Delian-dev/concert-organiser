import gui.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

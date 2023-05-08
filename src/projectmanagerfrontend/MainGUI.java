package projectmanagerfrontend;

import javax.swing.*;
import static globals.Globals.*;
public class MainGUI {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(2000,2000);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);

        JLabel initLabel = new JLabel(PROGRAM_INIT_TEXT);
        initLabel.setBounds(20,20,1000,100);
        mainFrame.add(initLabel);
    }
}

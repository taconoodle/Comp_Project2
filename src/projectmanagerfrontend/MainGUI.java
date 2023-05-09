package projectmanagerfrontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static globals.Globals.*;

public class MainGUI {

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1500, 1000);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);

        JLabel initLabel = new JLabel(PROGRAM_INIT_TEXT);
        initLabel.setBounds(20, 20, 1060, 10);
        mainFrame.add(initLabel);

        JButton executionButton = new JButton("Execute Programs.");
        executionButton.setBounds(650, 110, 180, 50);
        executionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame execFrame = new JFrame();
                execFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                execFrame.setSize(500, 500);
                execFrame.setLayout(null);
                execFrame.setVisible(true);

                ProgramExecutionGUI programExecutionMenu = new ProgramExecutionGUI(execFrame);
            }
        });
        mainFrame.add(executionButton);

        VmManagementGUI vmMenu = new VmManagementGUI(mainFrame);
        ProgramManagementGUI programMenu = new ProgramManagementGUI(mainFrame);
    }
}
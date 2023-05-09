package projectmanagerfrontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgramManagementGUI implements ActionListener {
    private JPanel mainPanel;
    private JButton createProgram;
    protected ProgramManagementGUI(JFrame frame) {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBounds(1100,100,400,1000);
        frame.add(mainPanel);

        createProgram = new JButton("Create a new Program");
        createProgram.setBounds(10, 10, 365, 50);
        createProgram.addActionListener(this);
        mainPanel.add(createProgram);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("O kokas exei megali psoli");
    }
}

package projectmanagerfrontend;

import projectmanagerbackend.ClusterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgramManagementGUI implements ActionListener {
    private JPanel mainPanel;
    private JButton createProgram;
    private ClusterGUI cluster;
    protected ProgramManagementGUI(JFrame frame, ClusterGUI cluster) {
        this.cluster = cluster;
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBounds(1100,100,400,1000);


        createProgram = new JButton("Create a new Program");
        createProgram.setBounds(10, 10, 365, 50);
        createProgram.addActionListener(this);
        mainPanel.add(createProgram);
        frame.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cluster.createProgramMenu();
    }
}

package projectmanagerfrontend;

import projectmanagerbackend.ClusterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class ProgramManagementGUI implements ActionListener {
    private JPanel mainPanel;
    private JButton createProgram;
    private JButton importProgramsFromFile;
    private ClusterGUI cluster;
    protected ProgramManagementGUI(JFrame frame, ClusterGUI cluster) {
        this.cluster = cluster;
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBounds(1100,100,400,1000);

        createProgram = new JButton("Create a new Program");
        createProgram.setBounds(10, 10, 180, 50);
        createProgram.addActionListener(this);
        mainPanel.add(createProgram);

        importProgramsFromFile = new JButton("Import Programs from file");
        importProgramsFromFile.setLayout(null);
        importProgramsFromFile.setBounds(200, 10, 182, 50);
        importProgramsFromFile.addActionListener(this);
        mainPanel.add(importProgramsFromFile);
        frame.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createProgram) {
            cluster.createProgramMenu();
        }
        if(e.getSource() == importProgramsFromFile) {
            programImporter();
        }
    }

    private void programImporter() {
        if (cluster.getNumOfVMs() == 0) {
            showMessageDialog(null, "There are no VMs. Please create a VM to use this function", null, WARNING_MESSAGE);
            return;
        }
        File progCfg = new File("cfg/programs.config");
        try {
            if(!progCfg.exists() || !cluster.createProgsFromConfig()) {
                showMessageDialog(null, "Program import failed or file missing!", null, WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            showMessageDialog(null, "Program import failed!", null, WARNING_MESSAGE);
        }
        cluster.sortProgramsByPriority(cluster.getMyProgs());
    }
}
package projectmanagerfrontend;

import projectmanagerbackend.ClusterGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static globals.Globals.*;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class MainGUI {

    public static void main(String[] args) {
        ClusterGUI cluster = new ClusterGUI();

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1500, 1000);
        mainFrame.setLayout(null);


        JLabel initLabel = new JLabel(PROGRAM_INIT_TEXT_GUI);
        initLabel.setBounds(15, 10, 1060, 50);
        mainFrame.add(initLabel);

        mainFrame.setVisible(true);

        JButton executionButton = new JButton("Execute Programs");
        executionButton.setBounds(650, 110, 180, 50);
        executionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cluster.getNumOfProgs() == 0) {
                    showMessageDialog(null, "There are no programs. Please create a program to use this function", null, WARNING_MESSAGE);
                    return;
                }
                try {
                    executePrograms(cluster);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        mainFrame.add(executionButton);

        VmManagementGUI vmMenu = new VmManagementGUI(mainFrame, cluster);
        ProgramManagementGUI programMenu = new ProgramManagementGUI(mainFrame, cluster);

    }

    private static void executePrograms(ClusterGUI cluster) throws IOException, InterruptedException {
        cluster.resetProgramAssignementAttempts();
        cluster.sortProgramsByPriority(cluster.getMyProgs());
        cluster.initialProgramPushInQueue();
        cluster.assignProgramsToVms();
    }
}
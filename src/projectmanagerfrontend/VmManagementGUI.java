package projectmanagerfrontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VmManagementGUI implements ActionListener {
    private JPanel mainPanel;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton reportButton;

    protected VmManagementGUI(JFrame frame) {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBounds(0,100,400,1000);
        frame.add(mainPanel);

        createButton = new JButton("Create a new VM.");
        createButton.setBounds(15, 10, 180, 50);
        createButton.addActionListener(this);
        mainPanel.add(createButton);

        updateButton = new JButton("Update a VM.");
        updateButton.setBounds(205, 10, 180, 50);
        updateButton.addActionListener(this);
        mainPanel.add(updateButton);

        deleteButton = new JButton("Delete a VM.");
        deleteButton.setBounds(15, 70, 180, 50);
        deleteButton.addActionListener(this);
        mainPanel.add(deleteButton);

        reportButton = new JButton("Show all VMs.");
        reportButton.setBounds(205, 70, 180, 50);
        reportButton.addActionListener(this);
        mainPanel.add(reportButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == createButton) {
            VmTypeChoiceMenu menu = new VmTypeChoiceMenu();
        }
        if(e.getSource() == updateButton) {
            System.out.println("poutsa");
        }
        if(e.getSource() == deleteButton) {
            System.out.println("poutsa");
        }
        if(e.getSource() == reportButton) {
            System.out.println("poutsa");
        }
    }
}
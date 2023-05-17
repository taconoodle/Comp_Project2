package projectmanagerfrontend;

import projectmanagerbackend.ClusterGUI;
import projectmanagerbackend.ErrorWindow;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import static javax.swing.JOptionPane.*;

public class VmManagementGUI implements ActionListener {
    private JPanel mainPanel;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton reportButton;
    private ClusterGUI cluster;

    protected VmManagementGUI(JFrame frame, ClusterGUI cluster) {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setBounds(0, 100, 400, 1000);
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

        this.cluster = cluster;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            VmTypeChoiceMenu menu = new VmTypeChoiceMenu(cluster);
        }
        if (e.getSource() == updateButton) {
            vmUpdater();
        }
        if (e.getSource() == deleteButton) {
            vmDeleter();
        }
        if (e.getSource() == reportButton) {
            vmDisplayer();
        }
    }

    private void vmDisplayer() {
        if (cluster.getNumOfVMs() == 0) {
            showMessageDialog(null, "There aer no VMs. Please create a VM to use this function", null, WARNING_MESSAGE);
            return;
        }
        String[] options = {"CHOOSE ONE", "ALL", "CANCEL"};
        int displayChoice = showOptionDialog(null, "Do you want to display all VMs or just one?", null,
                YES_NO_CANCEL_OPTION, PLAIN_MESSAGE, null, options, 0);

        JFrame vmValues = new JFrame();
        vmValues.setSize(500,500);
        vmValues.setVisible(true);
        vmValues.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JTextArea vmDataArea = new JTextArea();
        vmDataArea.setBounds(10, 10, 20, 20);
        vmDataArea.setLayout(null);

        JScrollPane scrollableArea = new JScrollPane(vmDataArea);
        scrollableArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        vmValues.add(scrollableArea);

        if (displayChoice == 0) {
            int id = chooseVmId();
            try {
                vmDataArea.setText(cluster.displayVmResources(id));
            } catch (IndexOutOfBoundsException nonExistentVm) {
                vmValues.dispose();
            }
        }
        if (displayChoice == 1) {
            vmDataArea.setText(cluster.displayAllVmResources());
        }
    }

    private void vmUpdater() {
        int vmId = 0;
        if (cluster.getNumOfVMs() == 0) {
            showMessageDialog(null, "There aer no VMs. Please create a VM to use this function", null, WARNING_MESSAGE);
            return;
        }
        vmId = chooseVmId();
        cluster.updateVmMenu(vmId);
    }

    private void vmDeleter() {
        int vmId = -1;
        if (cluster.getNumOfVMs() == 0) {
            showMessageDialog(null, "There are no VMs. Please create a VM to use this function", null, WARNING_MESSAGE);
            return;
        }
        vmId = chooseVmId();
        if (confirmation() == 0) {
            cluster.deleteVmMenu(vmId);
        }
    }

    private int confirmation() {
        return JOptionPane.showConfirmDialog(null, "Are you sure?");
    }

    private int chooseVmId() {
        int id = -1;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog("Type in the ID of the VM"));
        } catch (InputMismatchException e) {
            showMessageDialog(null, "Please type a number!", null, ERROR_MESSAGE);
        }
        return id;
    }   //Highway to the danger zone...

}
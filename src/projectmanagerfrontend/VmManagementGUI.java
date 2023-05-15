package projectmanagerfrontend;

import projectmanagerbackend.ClusterGUI;

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
    private ClusterGUI cluster;
    private int vmId;

    protected VmManagementGUI(JFrame frame, ClusterGUI cluster) {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
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

        this.cluster = cluster;
        vmId = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == createButton) {
            VmTypeChoiceMenu menu = new VmTypeChoiceMenu();
        }
        if(e.getSource() == updateButton) {
            vmUpdater();
        }
        if(e.getSource() == deleteButton) {
            System.out.println("poutsa");
        }
        if(e.getSource() == reportButton) {
            System.out.println("poutsa");
        }
    }

    private void vmUpdater() {
        if (cluster.getNumOfVMs() == 0) {
            //showErrorWindow("<html>There are no VMs.<br/>Create a VM to continue</html>");
        }
        chooseVmId();
        cluster.updateVmMenu(vmId);

    }

    private void chooseVmId() {
        JFrame idFrame = new JFrame();
        idFrame.setLayout(null);
        idFrame.setSize(500,200);
        idFrame.setVisible(true);

        JLabel info = new JLabel("Type in the ID of the VM");
        info.setBounds(10,10,200,20);
        info.setLayout(null);
        idFrame.add(info);

        JTextField idField = new JTextField();
        idField.setBounds(10,40,200,20);
        idField.setLayout(null);
        idFrame.add(idField);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBounds(180, 100,100,20);
        idFrame.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmId = Integer.parseInt(idField.getText());
            }
        });
        return;
    }
}
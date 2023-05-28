package projectmanagerfrontend;

import projectmanagerbackend.ClusterGUI;

import javax.swing.*;
import java.awt.event.*;
public class VmTypeChoiceMenu implements ActionListener {
    private JButton createPlainVm;
    private JButton createVmGpu;
    private JButton createVmNetworked;
    private JButton createVmNetworkedGpu;
    private JLabel infoText;
    private final ClusterGUI cluster;

    protected VmTypeChoiceMenu(ClusterGUI cluster) {
        this.cluster = cluster;
        JFrame choiceFrame = new JFrame();
        choiceFrame.setLayout(null);
        choiceFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        choiceFrame.setSize(600, 450);
        choiceFrame.setVisible(true);

        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(null);
        choicePanel.setBounds(0, 0, 550, 450);
        choiceFrame.add(choicePanel);

        createPlainVm = new JButton("Create a VM with CPU, RAM, OS and SSD space.");
        createPlainVm.setBounds(45, 10, 500, 50);
        createPlainVm.setLayout(null);
        createPlainVm.addActionListener(this);

        createVmGpu = new JButton("Create a VM with CPU, RAM, OS, SSD space and GPUs.");
        createVmGpu.setBounds(45, 110, 500, 50);
        createVmGpu.setLayout(null);
        createVmGpu.addActionListener(this);

        createVmNetworked = new JButton("Create a VM with CPU, RAM, OS, SSD space and Network Bandwidth.");
        createVmNetworked.setBounds(45, 210, 500, 50);
        createVmNetworked.setLayout(null);
        createVmNetworked.addActionListener(this);

        createVmNetworkedGpu = new JButton("Create a VM with CPU, RAM, OS, SSD space, Network Bandwidth and GPUs.");
        createVmNetworkedGpu.setBounds(45, 310, 500, 50);
        createVmNetworkedGpu.setLayout(null);
        createVmNetworkedGpu.addActionListener(this);

        choicePanel.add(createPlainVm);
        choicePanel.add(createVmGpu);
        choicePanel.add(createVmNetworked);
        choicePanel.add(createVmNetworkedGpu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createPlainVm){
            cluster.createVmMenu(1);
        }
        if(e.getSource() == createVmGpu){
            cluster.createVmMenu(2);
        }
        if(e.getSource() == createVmNetworked){
            cluster.createVmMenu(3);
        }
        if(e.getSource() == createVmNetworkedGpu){
            cluster.createVmMenu(4);
        }
    }
}

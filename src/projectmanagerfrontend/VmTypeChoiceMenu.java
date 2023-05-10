package projectmanagerfrontend;

import javax.swing.*;
import java.awt.event.*;
public class VmTypeChoiceMenu {
    private JButton createPlainVm;
    private JButton createVmGpu;
    private JButton createVmNetworked;
    private JButton createVmNetworkedGpu;
    private JLabel infoText;

    protected VmTypeChoiceMenu() {
        JFrame choiceFrame = new JFrame();
        choiceFrame.setLayout(null);
        choiceFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        choiceFrame.setSize(500, 450);
        choiceFrame.setVisible(true);

        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(null);
        choicePanel.setBounds(0, 0, 500, 450);
        choiceFrame.add(choicePanel);

        createPlainVm = new JButton("Create a VM with CPU, RAM, OS and SSD space.");
        createPlainVm.setBounds(15, 10, 450, 50);

        createVmGpu = new JButton("Create a VM with CPU, RAM, OS, SSD space and GPUs.");
        createVmGpu.setBounds(15, 110, 450, 50);

        createVmNetworked = new JButton("Create a VM with CPU, RAM, OS, SSD space and Network Bandwidth.");
        createVmNetworked.setBounds(15, 210, 450, 50);

        createVmNetworkedGpu = new JButton("Create a VM with CPU, RAM, OS, SSD space, Network Bandwidth and GPUs.");
        createVmNetworkedGpu.setBounds(15, 310, 450, 50);

        choicePanel.add(createPlainVm);
        choicePanel.add(createVmGpu);
        choicePanel.add(createVmNetworked);
        choicePanel.add(createVmNetworkedGpu);
    }
}

package projectmanagerfrontend;

import projectmanagerbackend.ClusterGUI;

import javax.swing.*;
import java.io.IOException;

public class ProgramExecutionGUI {
    private JPanel mainPanel;
    private ClusterGUI cluster;
    protected ProgramExecutionGUI(ClusterGUI cluster) throws IOException, InterruptedException {
        cluster.resetProgramAssignementAttempts();
        cluster.initialProgramPushInQueue();
        cluster.assignProgramsToVms();
    }
}

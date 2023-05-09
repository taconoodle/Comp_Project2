package projectmanagerfrontend;

import projectmanagerbackend.*;

import java.io.IOException;
import java.io.File;
import static globals.Globals.*;

public class ProjectCLI {
    public static void main(String[] args) throws IOException, InterruptedException {
        Cluster cluster = new Cluster();
        System.out.println (PROGRAM_INIT_TEXT);

        vmManagement(cluster);

        System.out.println("\nVM management completed. Moving to Program creation:");

        programManagement(cluster);

        System.out.println("\nProgram creation completed. Moving to Program execution:");

        cluster.initialProgramPushInQueue();
        cluster.assignProgramsToVms();
    }

    private static void vmManagement(Cluster cluster) throws IOException {
        File vmCfg = new File("cfg/vms.config");
        if (!vmCfg.exists() || !cluster.createVMsFromConfig()) {
            vmConfigNotFound(cluster);
        }
        cluster.displayAllVmResources();
    }

    private static void vmConfigNotFound(Cluster cluster) {
        System.out.println("No config files were found. Proceeding to CLI menu");
        while (true) {
            vmManagementUserMenu(cluster);
            if (cluster.getNumOfVMs() != 0) {
                break;
            }
            System.out.println("\nThere are no VMs. Create at least one VM before moving to Program creation.");
        }
    }

    private static void vmManagementUserMenu(Cluster cluster) {
        while (true) {
            System.out.println("\nPlease pick an option by pressing the number next to it:\n 1. Create a new VM\n" +
                    " 2. Update a currently existing VM.\n 3. Delete a VM.\n 4. Show a report with the resources of a certain VM or all of them.\n 5. Move on to the next stage.");
            int choice = cluster.getChoice();
            if (choice == 5) {
                break;
            }
            cluster.vmMenu(choice);
        }
    }

    private static void programManagement(Cluster cluster) throws IOException {
        File progCfg = new File("cfg/programs.config");
        if(!progCfg.exists() || !cluster.createProgsFromConfig()) {
            programConfigNotFound(cluster);
        }
    }

    private static void programConfigNotFound(Cluster cluster) {
        System.out.println("No config files were found. Proceeding to CLI menu.");
        while (true) {
            programManagementUserMenu(cluster);
            if (cluster.getNumOfProgs() != 0) {
                break;
            }
            System.out.println("\nThere are no programs. Create at least one program before moving to program execution.");
        }
    }

    private static void programManagementUserMenu(Cluster cluster) {
        while (true) {
            System.out.println("\nPlease pick an option by pressing the number next to it:\n1. Create a new Program\n" +
                    "2. Move on to Program execution");
            int choice = cluster.getChoice();
            if (choice == 2) {
                cluster.sortProgramsByPriority(cluster.getMyProgs());
                break;
            }
            cluster.createProgramMenu();
        }
    }
}

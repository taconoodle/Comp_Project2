package projectmanagerfrontend;

import projectmanagerbackend.*;

import java.io.IOException;

public class ProjectCLI {
    public static void main(String[] args) throws IOException, InterruptedException {
        Cluster cluster = new Cluster();
        System.out.println ("Cluster initialized.\nThere are 128 CPU cores, 256 GB RAM, 2048 GB of SSD space, 8 GPUs and 320 GB/s Internet Bandwidth available." +
                " The supported OSes are Windows, Ubuntu and Fedora.");

        while (true) {
            while (true) {
                System.out.println("\nPlease pick an option by pressing the number next to it:\n1. Create a new VM\n" +
                        "2. Update a currently existing VM.\n3. Delete a VM.\n4. Show a report with the resources of a certain VM or all of them.\n5. Move on to the next stage.");
                int choice = cluster.getChoice();
                if (choice == 5) {
                    break;
                }
                cluster.vmMenu(choice);
            }
            if(cluster.getNumOfVMs() != 0) {
                break;
            }
            System.out.println("\nThere are no VMs. Create at least one VM before moving to Program creation.");
        }

        System.out.println("\nVM management completed. Moving to Program creation:");

        while (true) {
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
            if (cluster.getNumOfProgs() != 0) {
                break;
            }
            System.out.println("\nThere are no programs. Create at least one program before moving to program execution.");
        }

        System.out.println("\nProgram creation completed. Moving to Program execution:");
        cluster.initialProgramPushInQueue();
        cluster.assignProgramsToVms();
    }
}

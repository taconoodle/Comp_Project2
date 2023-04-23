package projectmanagerfrontend;

import projectmanagerbackend.*;

import java.util.Scanner;

//should add a check in create and update that checks if the values are valid and asks again if they are not
public class ProjectCLI {
    public static void main(String[] args){
        Cluster cluster = new Cluster();
        System.out.println ("Cluster initialized.\nThere are 128 CPU cores, 256 GB RAM, 2048 GB of SSD space, 8 GPUs and 320 GB/s Internet Bandwidth available." +
                " The supported OSes are Windows, Ubuntu and Fedora.");
        while(true) {
            Scanner newScan = new Scanner(System.in);
            System.out.println ("\nPlease pick an option ny pressing the number next to it:\n1. Create a new VM\n" +
                    "2. Update a currently existing VM.\n3. Delete a VM.\n4. Show a report with the resources of a certain VM or all of them.\n5. Move on to the next stage.");
            int choice = cluster.getChoice();
            if (choice == 5) {
                break;
            }
            cluster.firstMenu(choice);
        }
    }
}

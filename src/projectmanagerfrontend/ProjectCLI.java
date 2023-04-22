package projectmanagerfrontend;

import projectmanageradmin.*;

//should add a check in create and update that checks if the values are valid and asks again if they are not
public class ProjectCLI {
    public static void main(String[] args){
        Cluster cluster = new Cluster();
        System.out.println ("Cluster initialized.\nThere are 128 CPU cores, 256 GB RAM, 2048 GB of SSD space, 8 GPUs and 320 GB/s Internet Bandwidth available.");
    }
}

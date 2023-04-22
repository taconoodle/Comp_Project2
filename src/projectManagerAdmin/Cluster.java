package projectManagerAdmin;

import globals.Globals.OperatingSystems;
import java.util.ArrayList;

public class Cluster {
    private int availableCPU;   //the amount of CPU cores that are available in the cluster
    private float availableRAM; //the amount of RAM that is available in the cluster in GBs
    private float availableDiskSpace;   //the amount of available SSD disk space available in the cluster in GBs
    private int availableGPU;   //the amount of GPUs available in the cluster
    private float availableBandwidth;   //the amount of Internet bandwidth that is free to utilize in GB/s
    private int numOfVMs; //how many VMs currently exist in the cluster
    private ArrayList<BaseVM> myVMs; //a dynamic array containing all the VMs, using memory dynamically as VMs are created

    private Cluster() { //initializes the variables of the Cluster
        availableCPU = 128;
        availableRAM = 256;
        availableDiskSpace = 2048;
        availableGPU = 8;
        availableBandwidth = 320;
        numOfVMs = 0;
        ArrayList<BaseVM> myVms = new ArrayList<BaseVM>();  //ArrayList that allocates memory dynamically, as the VMs can be infinite as long as there are resources
    }

    private int osExists(String os) {   //checks if the OS exists by iterating through all the supported OSes
        for (OperatingSystems checkOS : OperatingSystems.values()) {
            if (checkOS.name().equals(os.toUpperCase())) {  //checks if the String value of the OS in OperatingSystems equals to the String given. It also converts that the String given is in capitals for the check
                return 1;
            }
        }
        return -1;
    }

    private OperatingSystems getOS (String os) {    //returns the requested OS
        for (OperatingSystems requestedOS : OperatingSystems.values()) {
            if (requestedOS.name().equals(os.toUpperCase())) {  //checks if the String value of the OS in OperatingSystems equals to the String given. It also converts that the String given is in capitals for the check
                return requestedOS;
            }
        }
        return OperatingSystems.WINDOWS;
    }
    private int createVM(int cores, float ram, String os) { //Creates a new VM with only the basic functionality, after performing the necessary checks
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1) {
            System.out.println("System error: -1. Wrong values, not enough resources or OS not supported.");
            return -1;
        }
        VM newVM = new VM(numOfVMs, cores, ram, getOS(os));
        System.out.println("Successfully added new VM.");
        return 1;
    }
}
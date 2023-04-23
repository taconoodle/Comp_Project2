package projectmanageradmin;

import static globals.Globals.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cluster {
    private int availableCPU;   //the amount of CPU cores that are available in the cluster
    private double availableRAM; //the amount of RAM that is available in the cluster in GBs
    private double availableDiskSpace;   //the amount of available SSD disk space available in the cluster in GBs
    private int availableGPU;   //the amount of GPUs available in the cluster
    private double availableBandwidth;   //the amount of Internet bandwidth that is free to utilize in GB/s
    private int numOfVMs; //how many VMs currently exist in the cluster
    private ArrayList<BaseVM> myVMs; //a dynamic array containing all the VMs, using memory dynamically as VMs are created
    private int vmIdCount;

    public Cluster() { //initializes the variables of the Cluster
        availableCPU = 128;
        availableRAM = 256;
        availableDiskSpace = 2048;
        availableGPU = 8;
        availableBandwidth = 320;
        numOfVMs = 0;
        vmIdCount = 0;
        myVMs = new ArrayList<>(1);  //ArrayList that allocates memory dynamically, as the VMs can be infinite as long as there are resources
    }

    private void updateResources(int cores, double ram) {
        availableCPU -= cores;
        availableRAM -= ram;
    }
    private void updateResources(int cores, double ram, double diskSpace) {
        availableCPU -= cores;
        availableRAM -= ram;
        availableDiskSpace -= diskSpace;
    }
    private void updateResources(int cores, double ram, double diskSpace, int gpus) {
        availableCPU -= cores;
        availableRAM -= ram;
        availableDiskSpace -= diskSpace;
        availableGPU -= gpus;
    }
    private void updateResources(int cores, double ram, double diskSpace, double bandwidth) {
        availableCPU -= cores;
        availableRAM -= ram;
        availableDiskSpace -= diskSpace;
        availableBandwidth -= bandwidth;
    }
    private void updateResources(int cores, double ram, double diskSpace, double bandwidth, int gpus){
        availableCPU -= cores;
        availableRAM -= ram;
        availableDiskSpace -= diskSpace;
        availableBandwidth -= bandwidth;
        availableGPU -= gpus;
    }

    private void addResources(int cores, double ram) {
        availableCPU += cores;
        availableRAM += ram;
    }
    private void addResources(int cores, double ram, double diskSpace) {
        availableCPU += cores;
        availableRAM += ram;
        availableDiskSpace += diskSpace;
    }
    private void addResources(int cores, double ram, double diskSpace, int gpus) {
        availableCPU += cores;
        availableRAM += ram;
        availableDiskSpace += diskSpace;
        availableGPU += gpus;
    }
    private void addResources(int cores, double ram, double diskSpace, double bandwidth) {
        availableCPU += cores;
        availableRAM += ram;
        availableDiskSpace += diskSpace;
        availableBandwidth += bandwidth;
    }
    private void addResources(int cores, double ram, double diskSpace, double bandwidth, int gpus){
        availableCPU += cores;
        availableRAM += ram;
        availableDiskSpace += diskSpace;
        availableBandwidth += bandwidth;
        availableGPU += gpus;
    }

    private int osExists(String os) {   //checks if the OS exists by iterating through all the supported OSes
        for (OperatingSystems checkOS : OperatingSystems.values()) {
            if (checkOS.name().equals(os.toUpperCase())) {  //checks if the String value of the OS in OperatingSystems equals to the String given. It also converts that the String given is in capitals for the check
                return 1;
            }
        }
        return -1;
    }
    private int findVmById(int id) {
        for (int i = 0; i < numOfVMs; i++) {
            if(myVMs.get(i).getVmId() == id) {
                return i;
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

    public int createVM(int cores, double ram, String os) { //Creates a new VM with only the basic functionality, after performing the necessary checks
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1) {
            System.out.println("System error: -1. Wrong values, not enough resources or OS not supported.");
            return -1;
        }
        VM newVM = new VM(vmIdCount, cores, ram, getOS(os));
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram);
        System.out.println("Successfully added new VM with ID " + vmIdCount +".");
        vmIdCount++;
        return 1;
    }

    private int createPlainVM(int cores, double ram, String os, double diskSpace) {
        if((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace)) {
            System.out.println ("System error: -1. Wrong values, not enough resources or OS not supported.");
            return -1;
        }
        PlainVM newVM = new PlainVM(vmIdCount, cores, ram, getOS(os), diskSpace);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace);
        System.out.println("Successfully added new Plain VM with ID " + vmIdCount +".");
        vmIdCount++;
        return 1;
    }

    private int createVmGPU (int cores, double ram, String os, double diskSpace, int gpus) {
        if((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (gpus <= 0 || gpus > availableGPU)) {
            System.out.println ("System error: -1. Wrong values, not enough resources or OS not supported.");
            return -1;
        }
        VmGPU newVM = new VmGPU (vmIdCount, cores, ram, getOS(os), diskSpace, gpus);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, gpus);
        System.out.println ("Successfully added new VM with GPU with ID " + vmIdCount +".");
        vmIdCount++;
        return 1;
    }

    private int createVmNetworked (int cores, double ram, String os, double diskSpace, double bandwidth) {
        if((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth)) {
            System.out.println ("System error: -1. Wrong values, not enough resources or OS not supported.");
            return -1;
        }
        VmNetworked newVM = new VmNetworked(vmIdCount, cores, ram, getOS(os), diskSpace, bandwidth);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, bandwidth);
        System.out.println("Successfully added new networked VM with ID " + vmIdCount +".");
        vmIdCount++;
        return 1;
    }

    private int createVmNetworkedGPU (int cores, double ram, String os, double diskSpace, double bandwidth, int gpus) {
        if((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth) || (gpus <= 0 || gpus > availableGPU)) {
            System.out.println ("System error: -1. Wrong values, not enough resources or OS not supported.");
            return -1;
        }
        VmNetworkedGPU newVM = new VmNetworkedGPU(vmIdCount, cores, ram, getOS(os), diskSpace, bandwidth, gpus);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, bandwidth, gpus);
        System.out.println("Successfully added new networked VM with GPU with ID " + vmIdCount +".");
        vmIdCount++;
        return 1;
    }

    private void updateVM(int vmID, int cores, double ram, String os) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM)) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(),myVMs.get(vmID).getVmRam());
        myVMs.get(vmID).updateVM(cores, ram, os);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam());
    }

    private void updatePlainVM(int vmID, int cores, double ram, String os, double diskSpace) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace)) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace());
        myVMs.get(vmID).updateVM(cores, ram, os, diskSpace);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace());
    }

    private void updateVmGPU(int vmID, int cores, double ram, String os, double diskSpace, int gpus) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (gpus <= 0 || gpus > availableGPU)) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmGPUs());
        myVMs.get(vmID).updateVM(cores, ram, os, diskSpace, gpus);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmGPUs());
    }

    private void updateVmNetworked(int vmID, int cores, double ram, String os, double diskSpace, double bandwidth) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth <= MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth)) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth());
        myVMs.get(vmID).updateVM(cores, ram, os, diskSpace, bandwidth);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth());
    }

    private void updateVmNetworkedGPU(int vmID, int cores, double ram, String os, double diskSpace, double bandwidth, int gpus) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth) || (gpus <= 0 || gpus > availableGPU)) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth(), myVMs.get(vmID).getVmGPUs());
        myVMs.get(vmID).updateVM(cores, ram, os, diskSpace, bandwidth, gpus);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth(), myVMs.get(vmID).getVmGPUs());
    }

    private void deleteVM(int vmID, int cores, double ram) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(cores, ram);
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
    }
    private void deletePlainVM(int vmID, int cores, double ram, double diskSpace) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(cores, ram, diskSpace);
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
    }

    private void deleteVmGPU(int vmID, int cores, double ram, double diskSpace, int gpus) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(cores, ram, diskSpace, gpus);
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
    }

    private void deleteVmNetworked(int vmID, int cores, double ram, double diskSpace, double bandwidth) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(cores, ram, diskSpace, bandwidth);
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
    }

    private void deleteVmNetworkedGPU(int vmID, int cores, double ram, double diskSpace, double bandwidth, int gpus) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(cores, ram, diskSpace, bandwidth, gpus);
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
    }

    private void displayVmResources(int id) {
        System.out.println(myVMs.get(findVmById(id)).displayResources());
    }

    public void displayAllVmResources() {
        for (BaseVM vm : myVMs) {
            System.out.println(vm.displayResources());
        }
    }

    public int getChoice() {
        int choice = 0;
        Scanner newScan = new Scanner(System.in);
        try {
            choice = newScan.nextInt();
            if (choice <= 0 || choice > 5) {
                throw new InputMismatchException("InvalidChoiceNo");
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Choice number not valid.");
        }
        return choice;
    }

    public void firstMenu(int choice) {
        switch (choice) {
            case 1:
                createVmMenu();
                break;
            case 2:
                //updateVmMenu();
                break;
            case 3:
                //deleteVmMenu();
                break;
            case 4:
                //displayVmResourcesMenu();
                break;
        }
    }

    private void createVmMenu() {
        System.out.println("Please choose the type of VM you wish to create:\n1. VM with only CPU, RAM and OS.\n2. VM with CPU, RAM, OS and SSD space.\n" +
                "3. VM with CPU, RAM, OS, SSD space and GPU(s).\n4. VM with CPU, RAM, OS, SSD space and Internet Bandwidth.\n" +
                "5. VM with CPU, RAM, OS, SSD space, Internet Bandwidth and GPU(s).");
                Scanner newScan = new Scanner(System.in);
        switch (getChoice()) {
            case 1:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                int vmCores = newScan.nextInt();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                double vmRam = newScan.nextDouble();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                String vmOs = newScan.next();
                createVM(vmCores, vmRam, vmOs);
                break;
            case 2:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = newScan.nextInt();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = newScan.nextDouble();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                double vmDiskSpace = newScan.nextDouble();
                createPlainVM(vmCores, vmRam, vmOs, vmDiskSpace);
                break;
            case 3:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = newScan.nextInt();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = newScan.nextDouble();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newScan.nextDouble();
                System.out.println("Please type in the number of GPUs you wish to allocate to the VM");
                int vmGpus = newScan.nextInt();
                createVmGPU(vmCores, vmRam, vmOs, vmDiskSpace, vmGpus);
                break;
            case 4:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = newScan.nextInt();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = newScan.nextDouble();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newScan.nextDouble();
                System.out.println("Please type int the amount of GB/s this VM can use.");
                double vmBandwidth = newScan.nextDouble();
                createVmNetworked(vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth);
                break;
            case 5:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = newScan.nextInt();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = newScan.nextDouble();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newScan.nextDouble();
                System.out.println("Please type int the amount of GB/s this VM can use.");
                vmBandwidth = newScan.nextDouble();
                System.out.println("Please type in the number of GPUs you wish to allocate to the VM");
                vmGpus = newScan.nextInt();
                createVmNetworkedGPU(vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth, vmGpus);
                break;
        }
    }//And I think it's gonna be a long-long time, till touchdown brings us around
}
package projectmanagerbackend;

import static globals.Globals.*;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.*;

public class Cluster {
    private int availableCPU;   //the amount of CPU cores that are available in the cluster
    private double availableRAM; //the amount of RAM that is available in the cluster in GBs
    private double availableDiskSpace;   //the amount of available SSD disk space available in the cluster in GBs
    private int availableGPU;   //the amount of GPUs available in the cluster
    private double availableBandwidth;   //the amount of Internet bandwidth that is free to utilize in GB/s
    private int numOfVMs; //how many VMs currently exist in the cluster
    private ArrayList<VM> myVMs; //a dynamic array containing all the VMs, using memory dynamically as VMs are created
    private int vmIdCount;
    private ArrayList<Program> myProgs;
    private int numOfProgs;
    private BoundedQueue<Program> queue;

    private int getAvailableCPU() {
        return availableCPU;
    }

    private void setAvailableCPU(int availableCPU) {
        this.availableCPU = availableCPU;
    }

    private double getAvailableRAM() {
        return availableRAM;
    }

    private void setAvailableRAM(double availableRAM) {
        this.availableRAM = availableRAM;
    }

    private double getAvailableDiskSpace() {
        return availableDiskSpace;
    }

    private void setAvailableDiskSpace(double availableDiskSpace) {
        this.availableDiskSpace = availableDiskSpace;
    }

    private int getAvailableGPU() {
        return availableGPU;
    }

    private void setAvailableGPU(int availableGPU) {
        this.availableGPU = availableGPU;
    }

    private double getAvailableBandwidth() {
        return availableBandwidth;
    }

    private void setAvailableBandwidth(double availableBandwidth) {
        this.availableBandwidth = availableBandwidth;
    }

    public int getNumOfVMs() {
        return numOfVMs;
    }

    private void setNumOfVMs(int numOfVMs) {
        this.numOfVMs = numOfVMs;
    }

    private ArrayList<VM> getMyVMs() {
        return myVMs;
    }

    private void setMyVMs(ArrayList<VM> myVMs) {
        this.myVMs = myVMs;
    }

    private int getVmIdCount() {
        return vmIdCount;
    }

    private void setVmIdCount(int vmIdCount) {
        this.vmIdCount = vmIdCount;
    }

    public ArrayList<Program> getMyProgs() {
        return myProgs;
    }

    private void setMyProgs(ArrayList<Program> myProgs) {
        this.myProgs = myProgs;
    }

    public int getNumOfProgs() {
        return numOfProgs;
    }

    private void setNumOfProgs(int numOfProgs) {
        this.numOfProgs = numOfProgs;
    }

    public Cluster() { //initializes the variables of the Cluster
        availableCPU = AMOUNT_OF_CPU;
        availableRAM = AMOUNT_OF_RAM;
        availableDiskSpace = AMOUNT_OF_DISK_SPACE;
        availableGPU = AMOUNT_OF_GPU;
        availableBandwidth = NUM_OF_BANDWIDTH;
        numOfVMs = 0;
        vmIdCount = 0;
        numOfProgs = 0;
        myVMs = new ArrayList<>(1);  //ArrayList that allocates memory dynamically, as the VMs can be infinite as long as there are resources
        myProgs = new ArrayList<>(1);
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
        System.out.println("System error. No VM with this ID could be found.");
        return -1;
    }

    private VM getVmById(int id) {
        for (int i = 0; i < numOfVMs; i++) {
            if(myVMs.get(i).getVmId() == id) {
                return myVMs.get(i);
            }
        }
        return myVMs.get(0);
    }

    private int getVmType(VM vm) {
        if (vm instanceof VmNetworkedGPU) {
            return 4;
        }
        else if (vm instanceof VmNetworked) {
            return 3;
        }
        else if (vm instanceof VmGPU) {
            return 2;
        }
        else {
            return 1;
        }
    }

    private OperatingSystems getOS (String os) {    //returns the requested OS
        for (OperatingSystems requestedOS : OperatingSystems.values()) {
            if (requestedOS.name().equals(os.toUpperCase())) {  //checks if the String value of the OS in OperatingSystems equals to the String given. It also converts that the String given is in capitals for the check
                return requestedOS;
            }
        }
        return OperatingSystems.WINDOWS;
    }

    private void createPlainVM(int cores, double ram, String os, double diskSpace) {
        if((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace)) {
            System.out.println ("System error: -1. Wrong values, not enough resources or OS not supported.");
        }
        PlainVM newVM = new PlainVM(vmIdCount, cores, ram, getOS(os), diskSpace);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace);
        System.out.println("Successfully added new Plain VM with ID " + vmIdCount +".");
        vmIdCount++;
    }

    private void createVmGPU (int cores, double ram, String os, double diskSpace, int gpus) {
        if((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (gpus <= 0 || gpus > availableGPU)) {
            System.out.println ("System error: -1. Wrong values, not enough resources or OS not supported.");
        }
        VmGPU newVM = new VmGPU (vmIdCount, cores, ram, getOS(os), diskSpace, gpus);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, gpus);
        System.out.println ("Successfully added new VM with GPU with ID " + vmIdCount +".");
        vmIdCount++;
    }

    private void createVmNetworked (int cores, double ram, String os, double diskSpace, double bandwidth) {
        if((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth)) {
            System.out.println ("System error: -1. Wrong values, not enough resources or OS not supported.");
        }
        VmNetworked newVM = new VmNetworked(vmIdCount, cores, ram, getOS(os), diskSpace, bandwidth);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, bandwidth);
        System.out.println("Successfully added new networked VM with ID " + vmIdCount +".");
        vmIdCount++;
    }

    private void createVmNetworkedGPU (int cores, double ram, String os, double diskSpace, double bandwidth, int gpus) {
        if((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth) || (gpus <= 0 || gpus > availableGPU)) {
            System.out.println ("System error: -1. Wrong values, not enough resources or OS not supported.");
        }
        VmNetworkedGPU newVM = new VmNetworkedGPU(vmIdCount, cores, ram, getOS(os), diskSpace, bandwidth, gpus);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, bandwidth, gpus);
        System.out.println("Successfully added new networked VM with GPU with ID " + vmIdCount +".");
        vmIdCount++;
    }


    private void updatePlainVM(int vmID, int cores, double ram, String os, double diskSpace) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID " + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os), diskSpace);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace());
        System.out.println("Successfully updated VM.");
    }

    private void updateVmGPU(int vmID, int cores, double ram, String os, double diskSpace, int gpus) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (gpus <= 0 || gpus > availableGPU) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID " + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmGPUs());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os), diskSpace, gpus);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmGPUs());
        System.out.println("Successfully updated VM.");
    }

    private void updateVmNetworked(int vmID, int cores, double ram, String os, double diskSpace, double bandwidth) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth <= MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID " + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os), diskSpace, bandwidth);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth());
        System.out.println("Successfully updated VM.");
    }

    private void updateVmNetworkedGPU(int vmID, int cores, double ram, String os, double diskSpace, double bandwidth, int gpus) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth) || (gpus <= 0 || gpus > availableGPU) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID " + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth(), myVMs.get(vmID).getVmGPUs());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os), diskSpace, bandwidth, gpus);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth(), myVMs.get(vmID).getVmGPUs());
        System.out.println("Successfully updated VM.");
    }


    private void deletePlainVM(int vmID) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(myVMs.get(findVmById(vmID)).getVmCores(), myVMs.get(findVmById(vmID)).getVmRam(), myVMs.get(findVmById(vmID)).getVmDiskSpace());
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
        System.out.println("VM successfully deleted.");
    }

    private void deleteVmGPU(int vmID) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(myVMs.get(findVmById(vmID)).getVmCores(), myVMs.get(findVmById(vmID)).getVmRam(), myVMs.get(findVmById(vmID)).getVmDiskSpace(), myVMs.get(findVmById(vmID)).getVmGPUs());
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
        System.out.println("VM successfully deleted.");
    }

    private void deleteVmNetworked(int vmID) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(myVMs.get(findVmById(vmID)).getVmCores(), myVMs.get(findVmById(vmID)).getVmRam(), myVMs.get(findVmById(vmID)).getVmDiskSpace(), myVMs.get(findVmById(vmID)).getVmBandwidth());
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
        System.out.println("VM successfully deleted.");
    }

    private void deleteVmNetworkedGPU(int vmID) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(myVMs.get(findVmById(vmID)).getVmCores(), myVMs.get(findVmById(vmID)).getVmRam(), myVMs.get(findVmById(vmID)).getVmDiskSpace(),
                myVMs.get(findVmById(vmID)).getVmBandwidth(), myVMs.get(findVmById(vmID)).getVmGPUs());
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
        System.out.println("VM successfully deleted.");
    }

    private void displayVmResources(int id) {
        System.out.println(myVMs.get(findVmById(id)).displayResources());
    }

    public void displayAllVmResources() {
        for (VM vm : myVMs) {
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

    public void vmMenu(int choice) {
        switch (choice) {
            case 1:
                createVmMenu();
                break;
            case 2:
                updateVmMenu();
                break;
            case 3:
                deleteVmMenu();
                break;
            case 4:
                displayVmResourcesMenu();
                break;
        }
    }

    private int newIntegerInput () {
        int input;
        do {
            input = getIntegerWithCheck();
        } while (input == -1);
        return input;
    }   //And the sign said the words of the prophets are written on the subway walls and cement halls...

    private double newDoubleInput () {
        double input;
        do {
            input = getDoubleWithCheck();
        } while (input == -1);
        return input;
    }

    private void createVmMenu() {
        System.out.println("Please choose the type of VM you wish to create:\n1. VM with CPU, RAM, OS and SSD space.\n" +
                "2. VM with CPU, RAM, OS, SSD space and GPU(s).\n3. VM with CPU, RAM, OS, SSD space and Internet Bandwidth.\n" +
                "4. VM with CPU, RAM, OS, SSD space, Internet Bandwidth and GPU(s).");
                Scanner newScan = new Scanner(System.in);
        switch (getChoice()) {
            case 1:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                int vmCores = newIntegerInput();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                double vmRam = newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                String vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                double vmDiskSpace = newDoubleInput();
                createPlainVM(vmCores, vmRam, vmOs, vmDiskSpace);
                break;
            case 2:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = newIntegerInput();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newDoubleInput();
                System.out.println("Please type in the number of GPUs you wish to allocate to the VM");
                int vmGpus = newIntegerInput();
                createVmGPU(vmCores, vmRam, vmOs, vmDiskSpace, vmGpus);
                break;
            case 3:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = newIntegerInput();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newDoubleInput();
                System.out.println("Please type int the amount of GB/s this VM can use.");
                double vmBandwidth = newDoubleInput();
                createVmNetworked(vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth);
                break;
            case 4:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = newIntegerInput();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newDoubleInput();
                System.out.println("Please type int the amount of GB/s this VM can use.");
                vmBandwidth = newDoubleInput();
                System.out.println("Please type in the number of GPUs you wish to allocate to the VM");
                vmGpus = newIntegerInput();
                createVmNetworkedGPU(vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth, vmGpus);
                break;
        }
    }//And I think it's gonna be a long-long time, till touchdown brings us around

    private void updateVmMenu() {
        if (numOfVMs == 0) {
            System.out.println ("There are no VMs created in the cluster. Please create a new VM by selecting option 1.");
            return;
        }
        Scanner newScan = new Scanner(System.in);
        System.out.println("Please type in the ID of the VM you wish to update:");
        int id = newScan.nextInt();
        if(findVmById(id) == -1) {
            return;
        }
        int vmType = getVmType(getVmById(id));
        switch (vmType) {
            case 1:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                int vmCores = newIntegerInput();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                double vmRam = newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                String vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                double vmDiskSpace = newDoubleInput();
                updatePlainVM(id, vmCores, vmRam, vmOs, vmDiskSpace);
                break;
            case 2:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = newIntegerInput();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = newDoubleInput();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newDoubleInput();
                System.out.println("Please type in the updated number of GPUs you wish to allocate to the VM");
                int vmGpus = newIntegerInput();
                updateVmGPU(id, vmCores, vmRam, vmOs, vmDiskSpace, vmGpus);
                break;
            case 3:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = newIntegerInput();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = newDoubleInput();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newDoubleInput();
                System.out.println("Please type int the updated amount of GB/s this VM can use.");
                double vmBandwidth = newDoubleInput();
                updateVmNetworked(id, vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth);
                break;
            case 4:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = newIntegerInput();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = newDoubleInput();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newDoubleInput();
                System.out.println("Please type int the updated amount of GB/s this VM can use.");
                vmBandwidth = newDoubleInput();
                System.out.println("Please type in the updated number of GPUs you wish to allocate to the VM");
                vmGpus = newIntegerInput();
                updateVmNetworkedGPU(id, vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth, vmGpus);
                break;
        }
    }

    private int getIntegerWithCheck () {
        Scanner newScan = new Scanner(System.in);
        try {
            return newScan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("System error: Invalid input. Please enter a valid value.");
            return -1;
        }
    }

    private double getDoubleWithCheck () {
        Scanner newScan = new Scanner(System.in);
        try {
            return newScan.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("System error: Invalid input. Please enter a valid value.");
            return -1;
        }
    }

    private void deleteVmMenu() {
        if (numOfVMs == 0) {
            System.out.println ("There are no VMs created in the cluster. Please create a new VM by selecting option 1.");
            return;
        }
        Scanner newScan = new Scanner(System.in);
        System.out.println("Please type in the ID of the VM you wish to delete:");
        int id = newScan.nextInt();
        if(findVmById(id) == -1) {
            return;
        }
        int vmType = getVmType(getVmById(id));
        String deletionChoice;
        switch (vmType) {
            case 1:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                deletePlainVM(id);
                break;
            case 2:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                deleteVmGPU(id);
                break;
            case 3:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to  or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                deleteVmNetworked(id);
                break;
            case 4:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                deleteVmNetworkedGPU(id);
        }
    }

    private void displayVmResourcesMenu() {
        if (numOfVMs == 0) {
            System.out.println ("There are no VMs created in the cluster. Please create a new VM by selecting option 1.");
            return;
        }
        Scanner newScan = new Scanner(System.in);
        System.out.println("Do you want to display the resources of all VMs or choose one? Press 1 for all and 2 to pick.");
        int displayChoice = newScan.nextInt();
        if (displayChoice != 1 && displayChoice != 2) {
            System.out.println("Invalid choice number.");
            return;
        }
        if(displayChoice == 1) {
            displayAllVmResources();
            return;
        }
        System.out.println("Please type in the ID of the VM you wish to display the resources of:");
        int id = newScan.nextInt();
        if (findVmById(id) == -1) {
            return;
        }
        displayVmResources(id);
    }

    private void createProgram(int cores, int ram, int diskSpace, int gpu, int bandwidth, int expectedTime) {
        double[] totalResources = calculateTotalResources();
        if((cores <= 0 || cores > totalResources[0]) || (ram <= 0 || ram > totalResources[1]) || (diskSpace < 0 || diskSpace > totalResources[2]) || (gpu < 0 || gpu > totalResources[3]) ||
                (bandwidth < 0 || bandwidth > totalResources[4]) || expectedTime <= 0) {
            System.out.println("\nSystem error: Invalid values or not enough VMs to support to execute the program.");
            return;
        }
        Program newProg = new Program(cores, ram, diskSpace, gpu, bandwidth, expectedTime, calculateProgramPriority(totalResources, cores, ram, diskSpace, gpu, bandwidth));
        myProgs.add(newProg);
        numOfProgs++;
        System.out.println("\nSuccessfully added new Program with ID: " + newProg.getPID() + ".");
    }

    private double calculateProgramPriority(double[] resources, int cores, int ram, int diskSpace, int gpu, int bandwidth) {
        double priority = ((cores / resources[0]) + (ram / resources[1]));
        if (resources[2] != 0) {
            priority += diskSpace / resources[2];
        }
        if (resources[3] != 0) {
            priority += gpu / resources[3];
        }
        if (resources[4] != 0) {
            priority += bandwidth / resources[4];
        }
        return priority;
    }

    private double[] calculateTotalResources() {
        double[] totalResources = new double[5];
        totalResources[0] = AMOUNT_OF_CPU - availableCPU;
        totalResources[1] = AMOUNT_OF_RAM - availableRAM;
        totalResources[2] = AMOUNT_OF_DISK_SPACE - availableDiskSpace;
        totalResources[3] = AMOUNT_OF_GPU - availableGPU;
        totalResources[4] = NUM_OF_BANDWIDTH - availableBandwidth;
        return totalResources;
    }

    public void createProgramMenu () {
        System.out.println("Please type in the number of cores the program needs to be executed.");
        int cores = newIntegerInput();
        System.out.println("Please type in the amount of RAM the program needs to be executed.");
        int ram = newIntegerInput();
        System.out.println("Please type in the amount of SSD Disk Space the program needs to be executed.");
        int diskSpace = newIntegerInput();
        System.out.println("Please type in the number of GPUs the program needs to be executed.");
        int gpu = newIntegerInput();
        System.out.println("Please type in the amount of Bandwidth the program needs to be executed.");
        int bandwidth = newIntegerInput();
        System.out.println("Please type in the expected execution time of the program in seconds?");
        int expectedTime = newIntegerInput();
        createProgram(cores, ram, diskSpace, gpu, bandwidth, expectedTime * 1000);
    }//Lady, runnin' down to the riptide

    private void swap (ArrayList<Program> arr, int indx1, int indx2) {
        Program temp = arr.get(indx1);
        arr.set(indx1, arr.get(indx2));
        arr.set(indx2, temp);
    }

    public void sortProgramsByPriority(ArrayList<Program> programs) {
        for (int i = 0; i < numOfProgs; i++)  {
            for (int j = 1; j < numOfProgs - i; j++) {
                if (programs.get(j).getPPriority() < programs.get(j - 1).getPPriority()) {
                    swap(programs, j - 1, j);
                }
            }
        }
    }

    public void initialProgramPushInQueue() {
        queue = new BoundedQueue<Program>(numOfProgs);
        for (Program prog : myProgs) {
            queue.push(prog);
        }
    }

    private VM findVMWithLowestLoad (ArrayList<VM> vmsToCheck) {
        VM vmWithLowestLoad = vmsToCheck.get(0);
        for (VM vm : vmsToCheck) {
            if (vm.getVmLoad() < vmWithLowestLoad.getVmLoad()) {
                vmWithLowestLoad = vm;
            }
        }
        return vmWithLowestLoad;
    }

    public void assignProgramsToVms() throws IOException {
        long timeToSleep = 2L;
        TimeUnit time = TimeUnit.SECONDS;
        while (!queue.isEmpty()) {
            ArrayList<VM> possibleVMs = myVMs;  //A copy of the VM array. The program will check them from the ones with the least load to the most and if they are not able to support the Program, they will get removed from the list
            unassignFinishedPrograms();
            while (true) {
                if (possibleVMs.isEmpty()) {
                    queue.peek().setAssignAttempts(queue.peek().getAssignAttempts() + 1);
                    System.out.println("Program with ID " + queue.peek().getPID() + " was not able to be assigned to any VM to avoid overloading. Assignement attempts remaining: " + (MAX_ASSIGNMENT_ATTEMPTS - queue.peek().getAssignAttempts()));
                    if (queue.peek().getAssignAttempts() == 3) {
                        saveFailedProgram(queue.pop());
                    }
                    else {
                        queue.push(queue.pop());
                        try {
                            time.sleep(timeToSleep);
                        } catch (InterruptedException e) {
                            System.out.println("Interrupted while executing the programs.");
                        }
                        break;
                    }
                    VM vmToUse = findVMWithLowestLoad(possibleVMs);
                    if (vmToUse.getVmCores() < queue.peek().getPCores() || vmToUse.getVmRam() < queue.peek().getPRam() || vmToUse.getVmDiskSpace() < queue.peek().getPDiskSpace() || vmToUse.getVmGPUs() < queue.peek().getPGpu() || vmToUse.getVmBandwidth() < queue.peek().getPBandwidth()) {
                        possibleVMs.remove(vmToUse);
                    } else {
                        vmToUse.startWorkingOnProgram(queue.pop());
                        break;
                    }
                }
            }

        }
        waitUntilProgsAreDone();
    }

    private void unassignFinishedPrograms() {
        for (VM vm : myVMs) {
            for(Program prog : vm.getWorkingOn()){
                if (prog.getPExpectedTime() <= prog.getpExecTime()) {
                    vm.stopWorkingOnProgram(prog);
                    System.out.println("Program with ID " + prog.getPID() + " has finished executing and was deleted from the VM.");
                }
            }
        }
    }

    private void waitUntilProgsAreDone() {
        int vmsDone = 0;
        while(!(vmsDone == numOfVMs)){
            vmsDone = 0;
            unassignFinishedPrograms();
            for (VM vm : myVMs) {
                if (vm.getWorkingOn().isEmpty()) {   //Adds up the number for every vm that does not have any Programs, so when that number is equal to the nubmer of the VMs, every Program is done
                    vmsDone++;
                }
            }
        }
        System.out.println("\nAll programs are done executing.");
    }

    private void saveFailedProgram(Program prog) throws IOException {
        FileOutputStream fout = new FileOutputStream("./log/rejected.out");
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(prog);
        out.flush();
    }
}
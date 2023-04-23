package projectmanagerbackend;

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
    private ArrayList<Program> myProgs;
    private int numOfProgs;

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

    private BaseVM getVmById(int id) {
        for (int i = 0; i < numOfVMs; i++) {
            if(myVMs.get(i).getVmId() == id) {
                return myVMs.get(i);
            }
        }
        return myVMs.get(0);
    }

    private int getVmType(BaseVM vm) {
        if(vm instanceof VM) {
            return 1;
        }
        else if(vm instanceof PlainVM) {
            return 2;
        }
        else if(vm instanceof VmGPU) {
            return 3;
        }
        else if (vm instanceof VmNetworked) {
            return 4;
        }
        else {
            return 5;
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
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(),myVMs.get(vmID).getVmRam());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os));
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam());
    }

    private void updatePlainVM(int vmID, int cores, double ram, String os, double diskSpace) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os), diskSpace);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace());
    }

    private void updateVmGPU(int vmID, int cores, double ram, String os, double diskSpace, int gpus) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (gpus <= 0 || gpus > availableGPU) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmGPUs());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os), diskSpace, gpus);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmGPUs());
    }

    private void updateVmNetworked(int vmID, int cores, double ram, String os, double diskSpace, double bandwidth) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth <= MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os), diskSpace, bandwidth);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth());
    }

    private void updateVmNetworkedGPU(int vmID, int cores, double ram, String os, double diskSpace, double bandwidth, int gpus) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth) || (gpus <= 0 || gpus > availableGPU) || osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID" + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        addResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth(), myVMs.get(vmID).getVmGPUs());
        myVMs.get(vmID).updateVM(cores, ram, getOS(os), diskSpace, bandwidth, gpus);
        updateResources(myVMs.get(vmID).getVmCores(), myVMs.get(vmID).getVmRam(), myVMs.get(vmID).getVmDiskSpace(), myVMs.get(vmID).getVmBandwidth(), myVMs.get(vmID).getVmGPUs());
    }

    private void deleteVM(int vmID) {
        if (findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");   //COULD MAKE THIS A METHOD
            return;
        }
        addResources(myVMs.get(findVmById(vmID)).getVmCores(), myVMs.get(findVmById(vmID)).getVmRam());
        myVMs.remove(myVMs.get(findVmById(vmID)));
        numOfVMs--;
        System.out.println("VM successfully deleted.");
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
                int vmCores = newScan.nextInt();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                double vmRam = newScan.nextDouble();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                String vmOs = newScan.next();
                updateVM(id, vmCores, vmRam, vmOs);
                break;
            case 2:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = newScan.nextInt();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = newScan.nextDouble();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                double vmDiskSpace = newScan.nextDouble();
                updatePlainVM(id, vmCores, vmRam, vmOs, vmDiskSpace);
                break;
            case 3:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = newScan.nextInt();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = newScan.nextDouble();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newScan.nextDouble();
                System.out.println("Please type in the updated number of GPUs you wish to allocate to the VM");
                int vmGpus = newScan.nextInt();
                updateVmGPU(id, vmCores, vmRam, vmOs, vmDiskSpace, vmGpus);
                break;
            case 4:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = newScan.nextInt();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = newScan.nextDouble();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newScan.nextDouble();
                System.out.println("Please type int the updated amount of GB/s this VM can use.");
                double vmBandwidth = newScan.nextDouble();
                updateVmNetworked(id, vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth);
                break;
            case 5:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = newScan.nextInt();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = newScan.nextDouble();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = newScan.nextDouble();
                System.out.println("Please type int the updated amount of GB/s this VM can use.");
                vmBandwidth = newScan.nextDouble();
                System.out.println("Please type in the updated number of GPUs you wish to allocate to the VM");
                vmGpus = newScan.nextInt();
                updateVmNetworkedGPU(id, vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth, vmGpus);
                break;
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
        switch (vmType) {
            case 1:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                String deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                deleteVM(id);
                break;
            case 2:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                deletePlainVM(id);
                break;
            case 3:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                deleteVmGPU(id);
                break;
            case 4:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to  or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                deleteVmNetworked(id);
                break;
            case 5:
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
            System.out.println("System error: Invalid values or not enough VMs to support to execute the program.");
            return;
        }
        double priority = ((cores / totalResources[0]) + (ram / totalResources[1]) + (diskSpace / totalResources[2]) + (gpu / totalResources[3]) + (bandwidth / totalResources[4]));
        Program newProg = new Program(cores, ram, diskSpace, gpu, bandwidth, expectedTime, priority);
        myProgs.add(newProg);
        numOfProgs++;
        System.out.println("Successfully added new Program with ID: " + newProg.getPID() + ".");
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
        Scanner newScan = new Scanner(System.in);
        System.out.println("Please type in the number of cores the program needs to be executed.");
        int cores = newScan.nextInt();
        System.out.println("Please type in the amount of RAM the program needs to be executed.");
        int ram = newScan.nextInt();
        System.out.println("Please type in the amount of SSD Disk Space the program needs to be executed.");
        int diskSpace = newScan.nextInt();
        System.out.println("Please type in the number of GPUs the program needs to be executed.");
        int gpu = newScan.nextInt();
        System.out.println("Please type in the amount of Bandwidth the program needs to be executed.");
        int bandwidth = newScan.nextInt();
        System.out.println("Please type in the expected execution time of the program in seconds?");
        int expectedTime = newScan.nextInt();
        createProgram(cores, ram, diskSpace, gpu, bandwidth, expectedTime);
    }//Lady, runnin' down to the riptide
}
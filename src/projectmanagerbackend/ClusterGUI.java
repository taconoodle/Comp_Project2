package projectmanagerbackend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static javax.swing.JOptionPane.*;
import static globals.Globals.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ClusterGUI {
    private int availableCPU;   //the amount of CPU cores that are available in the cluster
    private double availableRAM; //the amount of RAM that is available in the cluster in GBs
    private double availableDiskSpace;   //the amount of available SSD disk space available in the cluster in GBs
    private int availableGPU;   //the amount of GPUs available in the cluster
    private double availableBandwidth;   //the amount of Internet bandwidth that is free to utilize in GB/s
    private int numOfVMs; //how many VMs currently exist in the cluster
    private ArrayList<VMGUI> myVMs; //a dynamic array containing all the VMs, using memory dynamically as VMs are created
    private int vmIdCount;
    private ArrayList<ProgramGUI> myProgs;
    private int numOfProgs;
    private BoundedQueue<ProgramGUI> queue;

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

    private ArrayList<VMGUI> getMyVMs() {
        return myVMs;
    }

    private void setMyVMs(ArrayList<VMGUI> myVMs) {
        this.myVMs = myVMs;
    }

    private int getVmIdCount() {
        return vmIdCount;
    }

    private void setVmIdCount(int vmIdCount) {
        this.vmIdCount = vmIdCount;
    }

    public ArrayList<ProgramGUI> getMyProgs() {
        return myProgs;
    }

    private void setMyProgs(ArrayList<ProgramGUI> myProgs) {
        this.myProgs = myProgs;
    }

    public int getNumOfProgs() {
        return numOfProgs;
    }

    private void setNumOfProgs(int numOfProgs) {
        this.numOfProgs = numOfProgs;
    }

    public ClusterGUI() { //initializes the variables of the Cluster
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

    private void updateResources(int cores, double ram, double diskSpace, double bandwidth, int gpus) {
        availableCPU -= cores;
        availableRAM -= ram;
        availableDiskSpace -= diskSpace;
        availableBandwidth -= bandwidth;
        availableGPU -= gpus;
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

    private void addResources(int cores, double ram, double diskSpace, double bandwidth, int gpus) {
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
            if (myVMs.get(i).getVmId() == id) {
                return i;
            }
        }
        new ErrorWindow("No VM with that ID could be found.");
        return -1;
    }

    private VMGUI getVmById(int id) {
        for (int i = 0; i < numOfVMs; i++) {
            if (myVMs.get(i).getVmId() == id) {
                return myVMs.get(i);
            }
        }
        return myVMs.get(0);
    }

    private int getVmType(VMGUI vm) {
        if (vm instanceof VmNetworkedGPUGUI) {
            return 4;
        } else if (vm instanceof VmNetworkedGUI) {
            return 3;
        } else if (vm instanceof VmGPUGUI) {
            return 2;
        } else {
            return 1;
        }
    }

    private OperatingSystems getOS(String os) {    //returns the requested OS
        for (OperatingSystems requestedOS : OperatingSystems.values()) {
            if (requestedOS.name().equals(os.toUpperCase())) {  //checks if the String value of the OS in OperatingSystems equals to the String given. It also converts that the String given is in capitals for the check
                return requestedOS;
            }
        }
        return OperatingSystems.WINDOWS;
    }

    private boolean createPlainVM(int cores, double ram, String os, double diskSpace) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace)) {
            new ErrorWindow("<html>System error: -1.</br>Wrong values, not enough resources or OS not supported.</html>");
            return false;
        }
        PlainVMGUI newVM = new PlainVMGUI(vmIdCount, cores, ram, getOS(os), diskSpace);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace);
        new ErrorWindow("Successfully added new Plain VM with ID " + vmIdCount + ".");
        vmIdCount++;
        return true;
    }

    private boolean createVmGPU(int cores, double ram, String os, double diskSpace, int gpus) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (gpus <= 0 || gpus > availableGPU)) {
            new ErrorWindow("<html>System error: -1.</br>Wrong values, not enough resources or OS not supported.</html>");
            return false;
        }
        VmGPUGUI newVM = new VmGPUGUI(vmIdCount, cores, ram, getOS(os), diskSpace, gpus);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, gpus);
        new ErrorWindow("Successfully added new Plain VM with ID " + vmIdCount + ".");
        vmIdCount++;
        return true;
    }

    private boolean createVmNetworked(int cores, double ram, String os, double diskSpace, double bandwidth) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth)) {
            new ErrorWindow("<html>System error: -1.</br>Wrong values, not enough resources or OS not supported.</html>");
            return false;
        }
        VmNetworkedGUI newVM = new VmNetworkedGUI(vmIdCount, cores, ram, getOS(os), diskSpace, bandwidth);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, bandwidth);
        new ErrorWindow("Successfully added new Plain VM with ID " + vmIdCount + ".");
        vmIdCount++;
        return true;
    }

    private boolean createVmNetworkedGPU(int cores, double ram, String os, double diskSpace, double bandwidth, int gpus) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || osExists(os) == -1 || (diskSpace <= 0 || diskSpace > availableDiskSpace) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > availableBandwidth) || (gpus <= 0 || gpus > availableGPU)) {
            new ErrorWindow("<html>System error: -1.</br>Wrong values, not enough resources or OS not supported.</html>");
            return false;
        }
        VmNetworkedGPUGUI newVM = new VmNetworkedGPUGUI(vmIdCount, cores, ram, getOS(os), diskSpace, bandwidth, gpus);
        myVMs.add(newVM);
        numOfVMs++;
        updateResources(cores, ram, diskSpace, bandwidth, gpus);
        new ErrorWindow("Successfully added new Plain VM with ID " + vmIdCount + ".");
        vmIdCount++;
        return true;
    }


    private void updatePlainVM(int vmID, int cores, double ram, String os, double diskSpace) {
        if ((cores <= 0 || cores > availableCPU) || (ram <= 0 || ram > availableRAM) || (diskSpace <= 0 || diskSpace > availableDiskSpace) || osExists(os) == -1) {
            new ErrorWindow("Values not valid, or not enough resources!");
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
            new ErrorWindow("Values not valid, or not enough resources!");
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
            new ErrorWindow("Values not valid, or not enough resources!");
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
            new ErrorWindow("Values not valid, or not enough resources!");
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

    public String displayVmResources(int id) throws IndexOutOfBoundsException {
        return myVMs.get(findVmById(id)).displayResources();
    }

    public String displayAllVmResources() {
        StringBuilder vmData = new StringBuilder();
        for (VMGUI vm : myVMs) {
            vmData.append(vm.displayResources()).append("\n");
        }
        return vmData.toString();
    }

    public int getChoice() {
        int choice = 0;
        Scanner newScan = new Scanner(System.in);
        try {
            choice = newScan.nextInt();
            if (choice <= 0 || choice > 5) {
                throw new InputMismatchException("InvalidChoiceNo");
            }
        } catch (InputMismatchException e) {
            showMessageDialog(null, "Choice number invalid!", null, WARNING_MESSAGE);
        }
        return choice;
    }

    public void createVmMenu(int vmType) {
        switch (vmType) {
            case 1: //plain VM

                int vmCores = getIntegerWithCheck(showInputDialog(null, "Please type in the number of CPU cores you wish to allocate to the VM."));
                double vmRam = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of RAM you wish to allocate to the VM."));
                String vmOs = showInputDialog(null, "Please type in the OS you wish to install to the VM.");
                double vmDiskSpace = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of SSD space you wish to allocate to the VM."));

                createPlainVM(vmCores, vmRam, vmOs, vmDiskSpace);

                break;

            case 2: //VM with GPU

                vmCores = getIntegerWithCheck(showInputDialog(null, "Please type in the number of CPU cores you wish to allocate to the VM."));
                vmRam = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of RAM you wish to allocate to the VM."));
                vmOs = showInputDialog(null, "Please type in the OS you wish to install to the VM.");
                vmDiskSpace = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of SSD space you wish to allocate to the VM."));
                int vmGpus = getIntegerWithCheck(showInputDialog(null, "Please type in the number of GPU cores you wish to allocate to the VM."));

                createVmGPU(vmCores, vmRam, vmOs, vmDiskSpace, vmGpus);

                break;
            case 3: //VM with Network

                vmCores = getIntegerWithCheck(showInputDialog(null, "Please type in the number of CPU cores you wish to allocate to the VM."));
                vmRam = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of RAM you wish to allocate to the VM."));
                vmOs = showInputDialog(null, "Please type in the OS you wish to install to the VM.");
                vmDiskSpace = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of SSD space you wish to allocate to the VM."));
                double vmBandwidth = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of bandwidth you wish to allocate to the VM."));

                createVmNetworked(vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth);

                break;
            case 4: //VM with Network and GPU

                vmCores = getIntegerWithCheck(showInputDialog(null, "Please type in the number of CPU cores you wish to allocate to the VM."));
                vmRam = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of RAM you wish to allocate to the VM."));
                vmOs = showInputDialog(null, "Please type in the OS you wish to install to the VM.");
                vmDiskSpace = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of SSD space you wish to allocate to the VM."));
                vmBandwidth = getDoubleWithCheck(showInputDialog(null, "Please type in the amount of bandwidth you wish to allocate to the VM."));
                vmGpus = getIntegerWithCheck(showInputDialog(null, "Please type in the number of GPU cores you wish to allocate to the VM."));

                createVmNetworkedGPU(vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth, vmGpus);

                break;
        }
    }//And I think it's gonna be a long-long time, till touchdown brings us around

    public void updateVmMenu(int vmId) {
        if (findVmById(vmId) == -1) {
            return;
        }
        int vmType = getVmType(getVmById(vmId));
        switch (vmType) {
            case 1:
                int vmCores = getIntegerWithCheck(showInputDialog(null, "Please type in the updated CPU cores value."));
                double vmRam = getDoubleWithCheck(showInputDialog(null, "Please type in the updated RAM value."));
                String vmOs = showInputDialog(null, "Please type in the updated OS.");
                double vmDiskSpace = getDoubleWithCheck(showInputDialog(null, "Please type in the updated SSD space value."));

                updatePlainVM(vmId, vmCores, vmRam, vmOs, vmDiskSpace);

                break;
            case 2:
                vmCores = getIntegerWithCheck(showInputDialog(null, "Please type in the updated CPU cores value."));
                vmRam = getDoubleWithCheck(showInputDialog(null, "Please type in the updated RAM value."));
                vmOs = showInputDialog(null, "Please type in the updated OS.");
                vmDiskSpace = getDoubleWithCheck(showInputDialog(null, "Please type in the updated SSD space value."));
                int vmGpus = getIntegerWithCheck(showInputDialog(null, "Please type in the update GPU cores value."));

                updateVmGPU(vmId, vmCores, vmRam, vmOs, vmDiskSpace, vmGpus);

                break;

            case 3:

                vmCores = getIntegerWithCheck(showInputDialog(null, "Please type in the updated CPU cores value."));
                vmRam = getDoubleWithCheck(showInputDialog(null, "Please type in the updated RAM value."));
                vmOs = showInputDialog(null, "Please type in the updated OS.");
                vmDiskSpace = getDoubleWithCheck(showInputDialog(null, "Please type in the updated SSD space value."));
                double vmBandwidth = getDoubleWithCheck(showInputDialog(null, "Please type in the updated bandwidth value."));

                updateVmNetworked(vmId, vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth);

                break;

            case 4:
                vmCores = getIntegerWithCheck(showInputDialog(null, "Please type in the updated CPU cores value."));
                vmRam = getDoubleWithCheck(showInputDialog(null, "Please type in the updated RAM value."));
                vmOs = showInputDialog(null, "Please type in the updated OS.");
                vmDiskSpace = getDoubleWithCheck(showInputDialog(null, "Please type in the updated SSD space value."));
                vmBandwidth = getDoubleWithCheck(showInputDialog(null, "Please type in the updated bandwidth value."));
                vmGpus = getIntegerWithCheck(showInputDialog(null, "Please type in the update GPU cores value."));

                updateVmNetworkedGPU(vmId, vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth, vmGpus);

                break;
        }
    }

    private int getIntegerWithCheck(String num) {
        while(true) {
            try {
                return parseInt(num);
            } catch (InputMismatchException e) {
                //error frame
                new ErrorWindow("Please enter valid values!");
                showMessageDialog(null, "Please type valid values!", null, ERROR_MESSAGE);
            }
        }
    }

    private double getDoubleWithCheck(String num) {
        while(true) {
            try {
                return parseDouble(num);
            } catch (InputMismatchException e) {
                //show error frame
                showMessageDialog(null, "Please enter valid values!", null, ERROR_MESSAGE);
            }
        }
    }

    public void deleteVmMenu(int vmId) {
        int vmType = getVmType(getVmById(vmId));
        String deletionChoice;
        switch (vmType) {
            case 1:
                deletePlainVM(vmId);
                break;
            case 2:
                deleteVmGPU(vmId);
                break;
            case 3:
                deleteVmNetworked(vmId);
                break;
            case 4:
                deleteVmNetworkedGPU(vmId);
                break;
        }
    }

    private void displayVmResourcesMenu() {
        if (numOfVMs == 0) {
            System.out.println("There are no VMs created in the cluster. Please create a new VM by selecting option 1.");
            return;
        }
        Scanner newScan = new Scanner(System.in);
        System.out.println("Do you want to display the resources of all VMs or choose one? Press 1 for all and 2 to pick.");
        int displayChoice = newScan.nextInt();
        if (displayChoice != 1 && displayChoice != 2) {
            System.out.println("Invalid choice number.");
            return;
        }
        if (displayChoice == 1) {
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

    private boolean createProgram(int cores, int ram, int diskSpace, int gpu, int bandwidth, int expectedTime) {
        double[] totalResources = calculateTotalResources();
        if ((cores <= 0 || cores > totalResources[0]) || (ram <= 0 || ram > totalResources[1]) || (diskSpace < 0 || diskSpace > totalResources[2]) || (gpu < 0 || gpu > totalResources[3]) ||
                (bandwidth < 0 || bandwidth > totalResources[4]) || expectedTime <= 0) {
            System.out.println("\nSystem error: Invalid values or not enough VMs to support to execute the program.");
            return false;
        }
        ProgramGUI newProg = new ProgramGUI(cores, ram, diskSpace, gpu, bandwidth, expectedTime, calculateProgramPriority(totalResources, cores, ram, diskSpace, gpu, bandwidth));
        myProgs.add(newProg);
        numOfProgs++;
        System.out.println("\nSuccessfully added new Program with ID: " + newProg.getPID() + ".");
        return true;
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

    public void createProgramMenu() {
        int programCores = getIntegerWithCheck(showInputDialog(null, "Please type in the amount of CPU cores the program needs."));
        int programRam = getIntegerWithCheck(showInputDialog(null, "Please type in the amount of RAM the program needs."));
        int programSsd = getIntegerWithCheck(showInputDialog(null, "Please type in the amount of SSD space the program needs."));
        int programBandwidth = getIntegerWithCheck(showInputDialog(null, "Please type in the amount of banwidth the program needs."));
        int programGpu = getIntegerWithCheck(showInputDialog(null, "Please type in the amount of GPU cores the program needs."));
        int expectedTime = getIntegerWithCheck(showInputDialog(null, "Please type in the amount of time in seconds the program needs."));

        createProgram(programCores, programRam, programSsd, programBandwidth, programGpu, expectedTime * 1000);
    }//Lady, runnin' down to the riptide

    private void swap(ArrayList<Program> arr, int indx1, int indx2) {
        Program temp = arr.get(indx1);
        arr.set(indx1, arr.get(indx2));
        arr.set(indx2, temp);
    }

    public void sortProgramsByPriority(ArrayList<Program> programs) {
        for (int i = 0; i < numOfProgs; i++) {
            for (int j = 1; j < numOfProgs - i; j++) {
                if (programs.get(j).getPPriority() < programs.get(j - 1).getPPriority()) {
                    swap(programs, j - 1, j);
                }
            }
        }
    }

    public void initialProgramPushInQueue() {   //pushes all the programs in a queue
        queue = new BoundedQueue<ProgramGUI>(numOfProgs);
        for (ProgramGUI prog : myProgs) {
            queue.push(prog);
        }
    }

    private VMGUI findVMWithLowestLoad(ArrayList<VMGUI> vmsToCheck) {    //checks all the VMs to find the one with the lowest load
        VMGUI vmWithLowestLoad = vmsToCheck.get(0);
        for (VMGUI vm : vmsToCheck) {
            if (vm.getVmLoad() < vmWithLowestLoad.getVmLoad()) {
                vmWithLowestLoad = vm;
            }
        }
        return vmWithLowestLoad;
    }

    public void assignProgramsToVms() throws IOException, InterruptedException {    //assigns the programs in the queue to the appropriate VMs until all the programs are removed from the queue
        File file = new File("log/rejected.out");
        file.delete();
        while (!queue.isEmpty()) {
            findVmToAssignProject();
        }
        waitUntilProgsAreDone();
    }

    private void findVmToAssignProject() throws IOException {   //checks all the VMs to find the one that can execute the head of the queue
        ArrayList<VMGUI> possibleVMs = new ArrayList<VMGUI>(myVMs);  //A copy of the VM array. The program will check them from the ones with the least load to the most and if they are not able to support the Program, they will get removed from the list
        while (true) {
            unassignFinishedPrograms();
            if (possibleVMs.isEmpty()) {
                programAssignementFailed();
                break;
            }
            VMGUI vmToUse = findVMWithLowestLoad(possibleVMs);
            if (attemptAssignProgramToVm(vmToUse)) {
                break;
            }
            possibleVMs.remove(vmToUse);
        }
    }

    private void programAssignementFailed() throws IOException {    //runs when a program is unable to be assigned to any VM
        long timeToSleep = 2L;
        TimeUnit time = TimeUnit.SECONDS;
        queue.peek().setAssignAttempts(queue.peek().getAssignAttempts() + 1);
        showMessageDialog(null, "Program with ID " + queue.peek().getPID() + " was not able to be assigned to any VM to avoid overloading. Assignement attempts remaining: " + (MAX_ASSIGNMENT_ATTEMPTS - queue.peek().getAssignAttempts()),
                null, ERROR_MESSAGE);
        if (queue.peek().getAssignAttempts() == 3) {
            saveFailedProgram(queue.pop());
        } else {
            ProgramGUI temp = queue.pop();
            queue.push(temp);
            try {
                time.sleep(timeToSleep);
            } catch (InterruptedException e) {
                showMessageDialog(null, "Interrupted while executing the programs.", null, ERROR_MESSAGE);
            }
        }
    }

    private boolean attemptAssignProgramToVm(VMGUI vm) {  //attempts to assign the program to the passed in VM
        if (vm.getVmCores() < queue.peek().getPCores() || vm.getVmRam() < queue.peek().getPRam() || vm.getVmDiskSpace() < queue.peek().getPDiskSpace() || vm.getVmGPUs() < queue.peek().getPGpu() || vm.getVmBandwidth() < queue.peek().getPBandwidth()) {
            return false;
        } else {
            vm.startWorkingOnProgram(queue.pop());
            return true;
        }
    }

    private void unassignFinishedPrograms() {   //checks every VM to find if any programs have finished executing and then deletes them from that VM
        for (VMGUI vm : myVMs) {
            ArrayList<ProgramGUI> workingOnCopy = new ArrayList<ProgramGUI>(vm.getWorkingOn());
            for (ProgramGUI prog : workingOnCopy) {
                prog.setCurrentExecTime(System.currentTimeMillis());
                prog.setPExecTime(prog.getCurrentExecTime() - prog.getPStartExecTime());
                if (prog.getPExpectedTime() <= prog.getPExecTime()) {
                    vm.stopWorkingOnProgram(prog);
                    showMessageDialog(null, "Program with ID " + prog.getPID() + " has finished executing and was deleted from the VM.");
                }
            }
        }
    }

    private void waitUntilProgsAreDone() throws InterruptedException {  //waits until all the programs that have been passed to VMs are done executing
        long timeToSleep = 1L;
        TimeUnit time = TimeUnit.SECONDS;
        int vmsDone = 0;

        while (vmsDone != numOfVMs) {
            vmsDone = 0;
            unassignFinishedPrograms();
            for (VMGUI vm : myVMs) {
                if (vm.getNumOfProgsInVm() == 0) {   //Adds up the number for every vm that does not have any Programs, so when that number is equal to the nubmer of the VMs, every Program is done
                    vmsDone++;
                }
            }
            time.sleep(timeToSleep);
        }
        showMessageDialog(null, "All programs are done executing.");
    }

    private void saveFailedProgram(ProgramGUI prog) throws IOException {   //runs when a program has failed 3 times. Saves the serialized program in a file
        File log = new File("log");
        if (!log.exists()) {
            log.mkdir();
        }
        FileOutputStream fout = new FileOutputStream("log/rejected.out", true);
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(prog);
        out.flush();
        out.close();
        showMessageDialog(null, "Program with ID " + prog.getPID() + " failed. A log was saved in: log/rejected.out", null, ERROR_MESSAGE);
    }

    public boolean createVMsFromConfig() throws IOException {     //CREATE THE FILE DIR
        BufferedReader reader = new BufferedReader(new FileReader("cfg/vms.config"));
        String vmLine = null;
        boolean vmCreated = false;
        while ((vmLine = reader.readLine()) != null) {
            Properties props = new Properties();
            String[] allResources = vmLine.split(","); //0: os, 1: cores, 2:ram, 3: ssd, 4: bandwidth/gpu, 5: gpu(if exists)
            for (String resource : allResources) {
                String resourceName = resource.split(":")[0].trim();
                props.setProperty(resourceName, resource.split(":")[1].trim());
            }

            int cores = 0, gpu = 0;
            double ram = 0, ssd = 0, bandwidth = 0;
            String os = props.getProperty("os");
            String coresStr = props.getProperty("cores");
            String ramStr = props.getProperty("ram");
            String ssdStr = props.getProperty("ssd");
            String bandwidthStr = props.getProperty("bandwidth");   //returns null if not found
            String gpuStr = props.getProperty("gpu");   //returns null if not found
            if (coresStr != null && ramStr != null && ssdStr != null) {
                cores = parseInt(coresStr);
                ram = parseDouble(ramStr);
                ssd = parseDouble(ssdStr);
            }
            if (bandwidthStr != null) {
                bandwidth = parseDouble(bandwidthStr);
            }
            if (gpuStr != null) {
                gpu = parseInt(gpuStr);
            }
            if (bandwidth == 0 && gpu == 0) {
                if (createPlainVM(cores, ram, os, ssd)) {
                    vmCreated = true;
                }
            } else if (bandwidth != 0 && gpu == 0) {
                if (createVmNetworked(cores, ram, os, ssd, bandwidth)) {
                    vmCreated = true;
                }
            } else if (bandwidth == 0 && gpu != 0) {
                if (createVmGPU(cores, ram, os, ssd, gpu)) {
                    vmCreated = true;
                }
            } else if (bandwidth != 0 && gpu != 0) {
                if (createVmNetworkedGPU(cores, ram, os, ssd, bandwidth, gpu)) {
                    vmCreated = true;
                }
            }
        }
        return vmCreated;
    }

    public boolean createProgsFromConfig() throws IOException {
        String progLine = null;
        BufferedReader reader = new BufferedReader(new FileReader("cfg/programs.config"));
        boolean programCreated = false;
        while ((progLine = reader.readLine()) != null) {
            Properties props = new Properties();
            String[] allResources = progLine.split(",");
            for (String resource : allResources) {
                String resourceName = resource.split(":")[0].trim();
                props.setProperty(resourceName, resource.split(":")[1].trim());
            }

            int cores = 0, gpu = 0, time = 0, ram = 0, ssd = 0, bandwidth = 0;
            String coresStr = props.getProperty("cores");
            String ramStr = props.getProperty("ram");
            String ssdStr = props.getProperty("ssd");
            String bandwidthStr = props.getProperty("bandwidth");   //returns null if not found
            String gpuStr = props.getProperty("gpu");   //returns null if not found
            String timeStr = props.getProperty("time");
            if (coresStr != null && ramStr != null && ssdStr != null && timeStr != null) {
                cores = parseInt(coresStr);
                ram = parseInt(ramStr);
                ssd = parseInt(ssdStr);
                time = parseInt(timeStr);
            }
            if (bandwidthStr != null) {
                bandwidth = parseInt(bandwidthStr);
            }
            if (gpuStr != null) {
                gpu = parseInt(gpuStr);
            }
            if (createProgram(cores, ram, ssd, gpu, bandwidth, time)) {
                programCreated = true;
            }
        }
        return programCreated;
    }
}
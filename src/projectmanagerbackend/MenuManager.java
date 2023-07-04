package projectmanagerbackend;

import java.util.Scanner;

public class MenuManager {
    private Cluster cluster;
    public MenuManager (Cluster cluster) {
        this.cluster = cluster;
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

    public void createVmMenu() {
        System.out.println("Please choose the type of VM you wish to create:\n1. VM with CPU, RAM, OS and SSD space.\n" +
                "2. VM with CPU, RAM, OS, SSD space and GPU(s).\n3. VM with CPU, RAM, OS, SSD space and Internet Bandwidth.\n" +
                "4. VM with CPU, RAM, OS, SSD space, Internet Bandwidth and GPU(s).");
        Scanner newScan = new Scanner(System.in);
        VMCreator creator = new VMCreator(cluster);
        switch (cluster.getChoice(1, 4)) {
            case 1:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                int vmCores = cluster.newIntegerInput();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                double vmRam = cluster.newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                String vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                double vmDiskSpace = cluster.newDoubleInput();
                creator.createPlainVm(vmCores, vmRam, vmOs, vmDiskSpace);
                break;
            case 2:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = cluster.newIntegerInput();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = cluster.newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = cluster.newDoubleInput();
                System.out.println("Please type in the number of GPUs you wish to allocate to the VM");
                int vmGpus = cluster.newIntegerInput();
                creator.createVmGPU(vmCores, vmRam, vmOs, vmDiskSpace, vmGpus);
                break;
            case 3:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = cluster.newIntegerInput();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = cluster.newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = cluster.newDoubleInput();
                System.out.println("Please type int the amount of GB/s this VM can use. Please note that you must allocate more than 4 GB/s to ensure the VM will work properly.");
                double vmBandwidth = cluster.newDoubleInput();
                creator.createVmNetworked(vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth);
                break;
            case 4:
                System.out.println("Please type in the number of CPU cores you wish to allocate to the VM.");
                vmCores = cluster.newIntegerInput();
                System.out.println("Please type in the amount of RAM you wish to allocate to the VM.");
                vmRam = cluster.newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = cluster.newDoubleInput();
                System.out.println("Please type int the amount of GB/s this VM can use. Please note that you must allocate more than 4 GB/s to ensure the VM will work properly.");
                vmBandwidth = cluster.newDoubleInput();
                System.out.println("Please type in the number of GPUs you wish to allocate to the VM.");
                vmGpus = cluster.newIntegerInput();
                creator.createVmNetworkedGPU(vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth, vmGpus);
                break;
        }
    }//And I think it's gonna be a long-long time, till touchdown brings us around

    private void updateVmMenu() {
        if (cluster.getNumOfVMs() == 0) {
            System.out.println("There are no VMs created in the cluster. Please create a new VM by selecting option 1.");
            return;
        }
        Scanner newScan = new Scanner(System.in);
        VMUpdater newUpdater = new VMUpdater(cluster);
        System.out.println("Please type in the ID of the VM you wish to update:");
        int id = cluster.newIntegerInput();
        if (cluster.findVmById(id) == -1) {
            return;
        }
        int vmType = cluster.getVmType(cluster.getVmById(id));
        switch (vmType) {
            case 1:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                int vmCores = cluster.newIntegerInput();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                double vmRam = cluster.newDoubleInput();
                System.out.println("Please type in the name of the OS you wish to use with the VM.");
                String vmOs = newScan.next();
                System.out.println("Please type in the amount of SSD space you wish to allocate to the VM.");
                double vmDiskSpace = cluster.newDoubleInput();
                newUpdater.updatePlainVM(id, vmCores, vmRam, vmOs, vmDiskSpace);
                break;
            case 2:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = cluster.newIntegerInput();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = cluster.newDoubleInput();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = cluster.newDoubleInput();
                System.out.println("Please type in the updated number of GPUs you wish to allocate to the VM.");
                int vmGpus = cluster.newIntegerInput();
                newUpdater.updateVmGPU(id, vmCores, vmRam, vmOs, vmDiskSpace, vmGpus);
                break;
            case 3:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = cluster.newIntegerInput();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = cluster.newDoubleInput();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = cluster.newDoubleInput();
                System.out.println("Please type int the updated amount of GB/s this VM can use. Please note that you must allocate more than 4 GB/s to ensure the VM will work properly.");
                double vmBandwidth = cluster.newDoubleInput();
                newUpdater.updateVmNetworked(id, vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth);
                break;
            case 4:
                System.out.println("Please type in the updated number of CPU cores you wish to allocate to the VM.");
                vmCores = cluster.newIntegerInput();
                System.out.println("Please type in the updated amount of RAM you wish to allocate to the VM.");
                vmRam = cluster.newDoubleInput();
                System.out.println("Please type in the updated name of the OS you wish to use with the VM.");
                vmOs = newScan.next();
                System.out.println("Please type in the updated amount of SSD space you wish to allocate to the VM.");
                vmDiskSpace = cluster.newDoubleInput();
                System.out.println("Please type int the updated amount of GB/s this VM can use. Please note that you must allocate more than 4 GB/s to ensure the VM will work properly.");
                vmBandwidth = cluster.newDoubleInput();
                System.out.println("Please type in the updated number of GPUs you wish to allocate to the VM.");
                vmGpus = cluster.newIntegerInput();
                newUpdater.updateVmNetworkedGPU(id, vmCores, vmRam, vmOs, vmDiskSpace, vmBandwidth, vmGpus);
                break;
        }
    }

    private void deleteVmMenu() {
        if (cluster.getNumOfVMs() == 0) {
            System.out.println("There are no VMs created in the cluster. Please create a new VM by selecting option 1.");
            return;
        }
        Scanner newScan = new Scanner(System.in);
        VMDeleter newDeleter = new VMDeleter(cluster);
        System.out.println("Please type in the ID of the VM you wish to delete:");
        int id = cluster.newIntegerInput();
        if (cluster.findVmById(id) == -1) {
            return;
        }
        int vmType = cluster.getVmType(cluster.getVmById(id));
        String deletionChoice;
        switch (vmType) {
            case 1:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                newDeleter.deletePlainVM(id);
                break;
            case 2:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                newDeleter.deleteVmGPU(id);
                break;
            case 3:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to  or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                newDeleter.deleteVmNetworked(id);
                break;
            case 4:
                System.out.println("Are you sure you want to delete the VM? This action is irreversible!. Type Y if you want to proceed or anything else to cancel.");
                deletionChoice = newScan.next().toUpperCase();
                if (!deletionChoice.equals("Y")) {
                    return;
                }
                newDeleter.deleteVmNetworkedGPU(id);
        }
    }

    private void displayVmResourcesMenu() {
        if (cluster.getNumOfVMs() == 0) {
            System.out.println("There are no VMs created in the cluster. Please create a new VM by selecting option 1.");
            return;
        }
        System.out.println("Do you want to display the resources of all VMs or choose one? Press 1 for all and 2 to pick.");
        int displayChoice = cluster.newIntegerInput();
        if (displayChoice != 1 && displayChoice != 2) {
            System.out.println("Invalid choice number.");
            return;
        }
        if (displayChoice == 1) {
            cluster.displayAllVmResources();
            return;
        }
        System.out.println("Please type in the ID of the VM you wish to display the resources of:");
        int id = cluster.newIntegerInput();
        if (cluster.findVmById(id) == -1) {
            return;
        }
        cluster.displayVmResources(id);
    }

    public void createProgramMenu() {
        ProgramManager progMger = new ProgramManager(cluster);
        System.out.println("Please type in the number of cores the program needs to be executed.");
        int cores = cluster.newIntegerInput();
        System.out.println("Please type in the amount of RAM the program needs to be executed.");
        int ram = cluster.newIntegerInput();
        System.out.println("Please type in the amount of SSD Disk Space the program needs to be executed.");
        int diskSpace = cluster.newIntegerInput();
        System.out.println("Please type in the number of GPUs the program needs to be executed.");
        int gpu = cluster.newIntegerInput();
        System.out.println("Please type in the amount of Bandwidth the program needs to be executed.");
        int bandwidth = cluster.newIntegerInput();
        System.out.println("Please type in the expected execution time of the program in seconds?");
        int expectedTime = cluster.newIntegerInput();
        progMger.createProgram(cores, ram, diskSpace, gpu, bandwidth, expectedTime * 1000);
    }//Lady, runnin' down to the riptide
}

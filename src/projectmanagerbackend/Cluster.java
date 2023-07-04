package projectmanagerbackend;

import static globals.Globals.*;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.Properties;

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

    protected int getAvailableCPU() {
        return availableCPU;
    }

    protected void setAvailableCPU(int availableCPU) {
        this.availableCPU = availableCPU;
    }

    protected double getAvailableRAM() {
        return availableRAM;
    }

    protected void setAvailableRAM(double availableRAM) {
        this.availableRAM = availableRAM;
    }

    protected double getAvailableDiskSpace() {
        return availableDiskSpace;
    }

    protected void setAvailableDiskSpace(double availableDiskSpace) {
        this.availableDiskSpace = availableDiskSpace;
    }

    protected int getAvailableGPU() {
        return availableGPU;
    }

    protected void setAvailableGPU(int availableGPU) {
        this.availableGPU = availableGPU;
    }

    protected double getAvailableBandwidth() {
        return availableBandwidth;
    }

    protected void setAvailableBandwidth(double availableBandwidth) {
        this.availableBandwidth = availableBandwidth;
    }

    public int getNumOfVMs() {
        return numOfVMs;
    }

    protected void setNumOfVMs(int numOfVMs) {
        this.numOfVMs = numOfVMs;
    }

    protected ArrayList<VM> getMyVMs() {
        return myVMs;
    }

    protected void setMyVMs(ArrayList<VM> myVMs) {
        this.myVMs = myVMs;
    }

    protected int getVmIdCount() {
        return vmIdCount;
    }

    protected void setVmIdCount(int vmIdCount) {
        this.vmIdCount = vmIdCount;
    }

    public ArrayList<Program> getMyProgs() {
        return myProgs;
    }

    protected void setMyProgs(ArrayList<Program> myProgs) {
        this.myProgs = myProgs;
    }

    public int getNumOfProgs() {
        return numOfProgs;
    }

    protected void setNumOfProgs(int numOfProgs) {
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
        myVMs = new ArrayList<>();  //ArrayList that allocates memory dynamically, as the VMs can be infinite as long as there are resources
        myProgs = new ArrayList<>();
    }

    protected int osExists(String os) {   //checks if the OS exists by iterating through all the supported OSes
        for (OperatingSystems checkOS : OperatingSystems.values()) {
            if (checkOS.name().equals(os.toUpperCase())) {  //checks if the String value of the OS in OperatingSystems equals to the String given. It also converts that the String given is in capitals for the check
                return 1;
            }
        }
        return -1;
    }

    protected int findVmById(int id) {
        for (int i = 0; i < numOfVMs; i++) {
            if (myVMs.get(i).getVmId() == id) {
                return i;
            }
        }
        System.out.println("System error. No VM with this ID could be found.");
        return -1;
    }

    protected VM getVmById(int id) {
        for (int i = 0; i < numOfVMs; i++) {
            if (myVMs.get(i).getVmId() == id) {
                return myVMs.get(i);
            }
        }
        return myVMs.get(0);
    }

    protected int getVmType(VM vm) {
        if (vm instanceof VmNetworkedGPU) {
            return 4;
        } else if (vm instanceof VmNetworked) {
            return 3;
        } else if (vm instanceof VmGPU) {
            return 2;
        } else {
            return 1;
        }
    }

    protected OperatingSystems getOS(String os) {    //returns the requested OS
        for (OperatingSystems requestedOS : OperatingSystems.values()) {
            if (requestedOS.name().equals(os.toUpperCase())) {  //checks if the String value of the OS in OperatingSystems equals to the String given. It also converts that the String given is in capitals for the check
                return requestedOS;
            }
        }
        return OperatingSystems.WINDOWS;
    }

    protected void displayVmResources(int id) {
        System.out.println(myVMs.get(findVmById(id)).displayResources());
    }

    public void displayAllVmResources() {
        for (VM vm : myVMs) {
            System.out.println(vm.displayResources());
        }
    }

    public int getChoice(int min, int max) {
        int choice = 0;
        Scanner newScan = new Scanner(System.in);
        try {
            choice = newScan.nextInt();
            if (choice < min || choice > max) {
                throw new InputMismatchException("InvalidChoiceNo");
            }
        } catch (InputMismatchException e) {
            System.out.println("Choice number not valid.");
        }
        return choice;
    }

    protected int newIntegerInput() {
        int input;
        do {
            input = getIntegerWithCheck();
        } while (input == -1);
        return input;
    }   //And the sign said the words of the prophets are written on the subway walls and cement halls...

    protected double newDoubleInput() {
        double input;
        do {
            input = getDoubleWithCheck();
        } while (input == -1);
        return input;
    }

    private int getIntegerWithCheck() {
        Scanner newScan = new Scanner(System.in);
        try {
            return newScan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("System error: Invalid input. Please enter a valid value.");
            return -1;
        }
    }

    private double getDoubleWithCheck() {
        Scanner newScan = new Scanner(System.in);
        try {
            return newScan.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("System error: Invalid input. Please enter a valid value.");
            return -1;
        }
    }

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
        queue = new BoundedQueue<Program>(numOfProgs);
        for (Program prog : myProgs) {
            queue.push(prog);
        }
    }

    private VM findVMWithLowestLoad(ArrayList<VM> vmsToCheck) {    //checks all the VMs to find the one with the lowest load
        VM vmWithLowestLoad = vmsToCheck.get(0);
        for (VM vm : vmsToCheck) {
            if (vm.getVmLoad() < vmWithLowestLoad.getVmLoad()) {
                vmWithLowestLoad = vm;
            }
        }
        return vmWithLowestLoad;
    }

    private VM findVMWithLowestLoadAfterProgramAssignement(ArrayList<VM> vmsToCheck, Program programToUse) {    //checks all the VMs to find the one with the lowest load
        VM vmWithLowestLoad = vmsToCheck.get(0);
        for (VM vm : vmsToCheck) {
            if (vm.calculateLoadAfterProgAssignement(programToUse) < vmWithLowestLoad.calculateLoadAfterProgAssignement(programToUse)) {
                vmWithLowestLoad = vm;
            }
        }
        return vmWithLowestLoad;
    }

    public void assignProgramsToVms() throws IOException, InterruptedException {    //assigns the programs in the queue to the appropriate VMs until all the programs are removed from the queue
        File file = new File("log/rejected.out");
        file.delete();
        while (!queue.isEmpty()) {
            findVmToAssignProgram();
        }
        waitUntilProgsAreDone();
    }

    private ArrayList<VM> findCompatibleVms(Program prog) {
        ArrayList<VM> compatibleVms = new ArrayList<VM>();
        for (VM vm : myVMs) {
            if (vm.getVmCores() >= prog.getPCores() && vm.getVmRam() >= prog.getPRam() && vm.getVmDiskSpace() >= prog.getPDiskSpace() &&
                    vm.getVmGPUs() >= prog.getPGpu() && vm.getVmBandwidth() >= prog.getPBandwidth()) {
                compatibleVms.add(vm);
            }
        }
        return compatibleVms;
    }

    private void findVmToAssignProgram() throws IOException {   //checks all the VMs to find the one that can execute the head of the queue
        ArrayList<VM> compatibleVMs = findCompatibleVms(queue.peek()); //A copy of the VM array. The program will check them from the ones with the least load to the most and if they are not able to support the Program, they will get removed from the list
        while (true) {
            unassignFinishedPrograms();
            if (compatibleVMs.isEmpty()) {
                programAssignementFailed();
                break;
            }
            VM vmToUse = findVMWithLowestLoadAfterProgramAssignement(compatibleVMs, queue.peek());
            if (attemptAssignProgramToVm(vmToUse)) {
                break;
            }
            compatibleVMs.remove(vmToUse);
        }
    }

    private void programAssignementFailed() throws IOException {    //runs when a program is unable to be assigned to any VM
        long timeToSleep = 2L;
        TimeUnit time = TimeUnit.SECONDS;
        queue.peek().setAssignAttempts(queue.peek().getAssignAttempts() + 1);
        System.out.println("Program with ID " + queue.peek().getPID() + " was not able to be assigned to any VM to avoid overloading. Assignement attempts remaining: " + (MAX_ASSIGNMENT_ATTEMPTS - queue.peek().getAssignAttempts()));

        if (queue.peek().getAssignAttempts() == 3) {
            saveFailedProgram(queue.pop());
        } else {
            Program temp = queue.pop();
            queue.push(temp);
            try {
                time.sleep(timeToSleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted while executing the programs.");
            }
        }
    }

    private boolean attemptAssignProgramToVm(VM vm) {  //attempts to assign the program to the passed in VM
        if (vm.getVmCores() < queue.peek().getPCores() || vm.getVmRam() < queue.peek().getPRam() || vm.getVmDiskSpace() < queue.peek().getPDiskSpace() ||
                vm.getVmGPUs() < queue.peek().getPGpu() || vm.getVmBandwidth() < queue.peek().getPBandwidth()) {
            return false;
        } else {
            vm.startWorkingOnProgram(queue.pop());
            return true;
        }
    }

    private void unassignFinishedPrograms() {   //checks every VM to find if any programs have finished executing and then deletes them from that VM
        for (VM vm : myVMs) {
            ArrayList<Program> workingOnCopy = new ArrayList<Program>(vm.getWorkingOn());
            for (Program prog : workingOnCopy) {
                prog.setCurrentExecTime(System.currentTimeMillis());
                prog.setPExecTime(prog.getCurrentExecTime() - prog.getPStartExecTime());
                if (prog.getPExpectedTime() <= prog.getPExecTime()) {
                    vm.stopWorkingOnProgram(prog);
                    System.out.println("Program with ID " + prog.getPID() + " has finished executing and was deleted from the VM.");
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
            for (VM vm : myVMs) {
                if (vm.getNumOfProgsInVm() == 0) {   //Adds up the number for every vm that does not have any Programs, so when that number is equal to the nubmer of the VMs, every Program is done
                    vmsDone++;
                }
            }
            time.sleep(timeToSleep);
        }
        System.out.println("\nAll programs are done executing.");
    }

    private void saveFailedProgram(Program prog) throws IOException {   //runs when a program has failed 3 times. Saves the serialized program in a file
        File log = new File("log");
        if (!log.exists()) {
            log.mkdir();
        }
        FileOutputStream fout = new FileOutputStream("log/rejected.out", true);
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(prog);
        out.flush();
        out.close();
        System.out.println("Program with ID " + prog.getPID() + " failed. A log was saved in: log/rejected.out");
    }

    public boolean createVMsFromConfig() throws IOException {     //CREATE THE FILE DIR
        BufferedReader reader = new BufferedReader(new FileReader("cfg/vms.config"));
        VMCreator creator = new VMCreator(this);
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
            try {
                if (coresStr != null && ramStr != null && ssdStr != null) {
                    cores = Integer.parseInt(coresStr);
                    ram = Double.parseDouble(ramStr);
                    ssd = Double.parseDouble(ssdStr);
                }
                if (bandwidthStr != null) {
                    bandwidth = Double.parseDouble(bandwidthStr);
                }
                if (gpuStr != null) {
                    gpu = Integer.parseInt(gpuStr);
                }
                if (bandwidth == 0 && gpu == 0) {
                    if (creator.createPlainVm(cores, ram, os, ssd)) {
                        vmCreated = true;
                    }
                } else if (bandwidth != 0 && gpu == 0) {
                    if (creator.createVmNetworked(cores, ram, os, ssd, bandwidth)) {
                        vmCreated = true;
                    }
                } else if (bandwidth == 0 && gpu != 0) {
                    if (creator.createVmGPU(cores, ram, os, ssd, gpu)) {
                        vmCreated = true;
                    }
                } else if (bandwidth != 0 && gpu != 0) {
                    if (creator.createVmNetworkedGPU(cores, ram, os, ssd, bandwidth, gpu)) {
                        vmCreated = true;
                    }
                }
            } catch (NumberFormatException ignored) {

            }
        }
        return vmCreated;
    }

    public boolean createProgsFromConfig() throws IOException {
        String progLine = null;
        BufferedReader reader = new BufferedReader(new FileReader("cfg/programs.config"));
        ProgramManager progMger = new ProgramManager(this);
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
            try {
                if (coresStr != null && ramStr != null && ssdStr != null && timeStr != null) {
                    cores = Integer.parseInt(coresStr);
                    ram = Integer.parseInt(ramStr);
                    ssd = Integer.parseInt(ssdStr);
                    time = Integer.parseInt(timeStr);
                }
                if (bandwidthStr != null) {
                    bandwidth = Integer.parseInt(bandwidthStr);
                }
                if (gpuStr != null) {
                    gpu = Integer.parseInt(gpuStr);
                }
                if (progMger.createProgram(cores, ram, ssd, gpu, bandwidth, time)) {
                    programCreated = true;
                }
            } catch (NumberFormatException ignored) {

            }
        }
        return programCreated;
    }
}
package projectmanagerbackend;

import globals.Globals.OperatingSystems;

import java.util.ArrayList;

public abstract class VM {
    private int vmId;
    private int vmCores;
    private double vmRam;
    private OperatingSystems vmOS;
    private double vmLoad;
    private int allocatedCores;
    private double allocatedRam;
    private ArrayList<Program> workingOn;
    private int numOfProgsInVm;

    protected VM(int id, int cores, double ram, OperatingSystems os) {
        vmId = id;
        vmCores = cores;
        vmRam = ram;
        vmOS = os;
        vmLoad = 0;
        allocatedCores = 0;
        allocatedRam = 0;
        workingOn = new ArrayList<Program>();
    }

    public int getVmId() {
        return vmId;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public int getVmCores() {
        return vmCores;
    }

    public void setVmCores(int vmCores) {
        this.vmCores = vmCores;
    }

    public double getVmRam() {
        return vmRam;
    }

    public void setVmRam(double vmRam) {
        this.vmRam = vmRam;
    }


    public OperatingSystems getVmOS() {
        return vmOS;
    }

    public void setVmOS(OperatingSystems vmOS) {
        this.vmOS = vmOS;
    }

    protected abstract void updateVM(Object... parameters);
    //every VM class should define this class to update its values according to their respective variables
    //the parameters are of type Object with the dots showing that they take the form of an array.
    //For example, the first parameter could an integer and is parameters[0], the second could be a String and is parameters[1]


    protected abstract String displayResources();

    protected double getVmDiskSpace() {
        return 0;
    }


    protected void setVmDiskSpace(double vmDiskSpace) {
        return;
    }

    protected int getVmGPUs() {
        return 0;
    }

    protected void setVmGPUs(int vmGPUs) {
        return;
    }

    protected double getVmBandwidth() {
        return 0;
    }

    protected void setVmBandwidth(double vmBandwidth) {
        return;
    }

    protected double getVmLoad() {
        return vmLoad;
    }

    protected void setVmLoad(double vmLoad) {
        this.vmLoad = vmLoad;
    }

    public int getAllocatedCores() {
        return allocatedCores;
    }

    public void setAllocatedCores(int allocatedCores) {
        this.allocatedCores = allocatedCores;
    }

    public double getAllocatedRam() {
        return allocatedRam;
    }

    public void setAllocatedRam(double allocatedRam) {
        this.allocatedRam = allocatedRam;
    }

    public double getAllocatedDiskSpace() {
        return 0;
    }

    public void setAllocatedDiskSpace(double allocatedDiskSpace) {
    }

    public int getAllocatedGPUs() {
        return 0;
    }

    public void setAllocatedGPUs(int allocatedGPUs) {
    }

    public double getAllocatedBandwidth() {
        return 0;
    }

    public void setAllocatedBandwidth(double allocatedBandwidth) {
    }

    protected ArrayList<Program> getWorkingOn() {
        return workingOn;
    }

    public void setWorkingOn(ArrayList<Program> workingOn) {
        this.workingOn = workingOn;
    }

    protected int getNumOfProgsInVm() {
        return numOfProgsInVm;
    }

    public void setNumOfProgsInVm(int numOfProgsInVm) {
        this.numOfProgsInVm = numOfProgsInVm;
    }

    protected abstract double calculateVmLoad();

    protected abstract double calculateLoadAfterProgAssignement(Program prog);


    protected void startWorkingOnProgram(Program prog) {
        prog.setPStartExecTime(System.currentTimeMillis());
        allocatedCores += prog.getPCores();
        allocatedRam += prog.getPRam();

        vmCores -= prog.getPCores();
        vmRam -= prog.getPRam();

        workingOn.add(prog);
        numOfProgsInVm++;
    }

    protected void stopWorkingOnProgram(Program prog) {
        allocatedCores -= prog.getPCores();
        allocatedRam -= prog.getPRam();

        vmCores += prog.getPCores();
        vmRam += prog.getPRam();

        workingOn.remove(prog);
        numOfProgsInVm--;
    }
}
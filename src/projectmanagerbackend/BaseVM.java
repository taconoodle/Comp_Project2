package projectmanagerbackend;

import globals.Globals.OperatingSystems;

public abstract class BaseVM {
    private int vmId;   //the ID of the VM which is a sequential integer that depends on how many VMs exist
    private int vmCores;    //the amount of CPU cores the VM can utilize
    private double vmRam;    //the amount of RAM the VM can utilize
    private double vmDiskSpace;  //the amount of SSD disk space the VM can utilize
    private int vmGPUs; //the amount of GPUs the VM can utilize
    private double vmBandwidth;  //the amount of network bandwidth the VM can utilize
    private OperatingSystems vmOS;  //the OS the VM is using

    protected BaseVM(int id, int cores, double ram, OperatingSystems os) {
        vmId = id;
        vmCores  = cores;
        vmRam = ram;
        vmOS = os;
    }

    protected int getVmId() {
        return vmId;
    }

    private void setVmId(int vmId) {
        this.vmId = vmId;
    }

    protected int getVmCores() {
        return vmCores;
    }

    protected void setVmCores(int vmCores) {
        this.vmCores = vmCores;
    }

    protected double getVmRam() {
        return vmRam;
    }

    protected void setVmRam(double vmRam) {
        this.vmRam = vmRam;
    }

    protected double getVmDiskSpace() {
        return vmDiskSpace;
    }

    protected void setVmDiskSpace(double vmDiskSpace) {
        this.vmDiskSpace = vmDiskSpace;
    }

    protected int getVmGPUs() {
        return vmGPUs;
    }

    protected void setVmGPUs(int vmGPUs) {
        this.vmGPUs = vmGPUs;
    }

    protected double getVmBandwidth() {
        return vmBandwidth;
    }

    protected void setVmBandwidth(double vmBandwidth) {
        this.vmBandwidth = vmBandwidth;
    }

    protected OperatingSystems getVmOS() {
        return vmOS;
    }

    protected void setVmOS(OperatingSystems vmOS) {
        this.vmOS = vmOS;
    }

    protected abstract void updateVM(Object... parameters);
    //every VM class should define this class to update its values according to their respective variables
    //the parameters are of type Object with the dots showing that they take the form of an array.
    //For example, the first parameter could an integer and is parameters[0], the second could be a String and is parameters[1]

    protected abstract String displayResources();

}
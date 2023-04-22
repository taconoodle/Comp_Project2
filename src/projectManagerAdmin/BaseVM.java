package projectManagerAdmin;

import globals.Globals.OperatingSystems;

public abstract class BaseVM {
    private int vmId;   //the ID of the VM which is a sequential integer that depends on how many VMs exist
    private int vmCores;    //the amount of CPU cores the VM can utilize
    private float vmRam;    //the amount of RAM the VM can utilize
    private float vmDiskSpace;  //the amount of SSD disk space the VM can utilize
    private int vmGPUs; //the amount of GPUs the VM can utilize
    private float vmBandwidth;  //the amount of network bandwidth the VM can utilize
    private OperatingSystems vmOS;  //the OS the VM is using

    protected BaseVM(int id, int cores, float ram, OperatingSystems os) {
        vmId = id;
        vmCores  = cores;
        vmRam = ram;
        vmOS = os;
    }

    private int getVmId() {
        return vmId;
    }

    private void setVmId(int vmId) {
        this.vmId = vmId;
    }

    private int getVmCores() {
        return vmCores;
    }

    protected void setVmCores(int vmCores) {
        this.vmCores = vmCores;
    }

    private float getVmRam() {
        return vmRam;
    }

    protected void setVmRam(float vmRam) {
        this.vmRam = vmRam;
    }

    private float getVmDiskSpace() {
        return vmDiskSpace;
    }

    protected void setVmDiskSpace(float vmDiskSpace) {
        this.vmDiskSpace = vmDiskSpace;
    }

    private int getVmGPUs() {
        return vmGPUs;
    }

    protected void setVmGPUs(int vmGPUs) {
        this.vmGPUs = vmGPUs;
    }

    private float getVmBandwidth() {
        return vmBandwidth;
    }

    protected void setVmBandwidth(float vmBandwidth) {
        this.vmBandwidth = vmBandwidth;
    }

    private OperatingSystems getVmOS() {
        return vmOS;
    }

    protected void setVmOS(OperatingSystems vmOS) {
        this.vmOS = vmOS;
    }

    protected abstract void updateVM(Object... parameters);
    //every VM class should define this class to update its values according to their respective variables
    //the parameters are of type Object with the dots showing that they take the form of an array.
    //For example, the first parameter could an integer and is parameters[0], the second could be a String and is parameters[1]

}
package projectmanagerbackend;

import globals.Globals.OperatingSystems;

public abstract class VM {
    private int vmId;
    private int vmCores;
    private double vmRam;
    private OperatingSystems vmOS;

    protected VM(int id, int cores, double ram, OperatingSystems os) {
        vmId = id;
        vmCores  = cores;
        vmRam = ram;
        vmOS = os;
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

    protected abstract double getVmDiskSpace();


    protected abstract void setVmDiskSpace(double vmDiskSpace);

    protected abstract int getVmGPUs();

    protected abstract void setVmGPUs(int vmGPUs);

    protected abstract double getVmBandwidth();

    protected abstract void setVmBandwidth(double vmBandwidth);
}

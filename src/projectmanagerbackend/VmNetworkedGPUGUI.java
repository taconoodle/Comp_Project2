package projectmanagerbackend;

import globals.Globals;

public class VmNetworkedGPUGUI extends VmNetworked {

    private int vmGPUs;
    private int allocatedGPUs;
    protected VmNetworkedGPUGUI(int id, int cores, double ram, Globals.OperatingSystems os, double diskSpace, double bandwidth, int gpus) {
        super(id, cores, ram, os, diskSpace, bandwidth);
        vmGPUs = gpus;
    }

    @Override
    public int getVmGPUs() {
        return vmGPUs;
    }

    @Override
    public void setVmGPUs(int vmGPUs) {
        this.vmGPUs = vmGPUs;
    }

    @Override
    public int getAllocatedGPUs() {
        return allocatedGPUs;
    }

    @Override
    public void setAllocatedGPUs(int allocatedGPUs) {
        this.allocatedGPUs = allocatedGPUs;
    }

    @Override
    protected void updateVM (Object... parameters) {
        if(parameters[0] != null) {
            super.setVmCores((int) parameters[0]); //first parameter should be the updated amount of cores
        }
        if(parameters[1] != null) {
            super.setVmRam((double) parameters[1]);    //second parameter should be the updated amount of ram
        }
        if(parameters[2] != null) {
            super.setVmOS((Globals.OperatingSystems) parameters[2]);  //third parameter should be the updated OS
        }
        if(parameters[3] != null) {
            super.setVmDiskSpace((double) parameters[3]);   //fourth parameter should be the updated disk space in the SSD allocated to the VM
        }
        if(parameters[4] != null) {
            super.setVmBandwidth((double) parameters[4]);   //fifth parameter should be the updated amount of bandwidth
        }
        if(parameters[5] != null) {
            vmGPUs = (int) parameters[5]; //sixth parameter should be the updated amount of GPUs
        }
    }

    @Override
    protected String displayResources() {
        return super.displayResources() + "\tVM GPUs: " + getVmGPUs();
    }

    @Override
    protected double calculateVmLoad() {
        double vmLoad = super.calculateVmLoad();
        if (getAllocatedGPUs() != 0) {
            vmLoad += ( (double) getAllocatedGPUs() / (double) getVmGPUs());
        }
        return vmLoad / 5;
    }

    @Override
    protected void updateVmResources(String mode) {
        switch (mode) {
            case "commit":
                super.updateVmResources("commit");
                vmGPUs -= allocatedGPUs;
                break;
            case "release":
                super.updateVmResources("release");
                vmGPUs += allocatedGPUs;
                break;
        }

    }

    @Override
    protected void startWorkingOnProgram(Program prog) {
        allocatedGPUs -= prog.getPGpu();
        super.startWorkingOnProgram(prog);
    }
    @Override
    protected void stopWorkingOnProgram(Program prog) {
        allocatedGPUs += prog.getPGpu();
        super.stopWorkingOnProgram(prog);
    }
}

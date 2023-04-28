package projectmanagerbackend;

import globals.Globals;

public class VmNetworkedGPU extends VmNetworked {

    private int vmGPUs;
    protected VmNetworkedGPU(int id, int cores, double ram, Globals.OperatingSystems os, double diskSpace, double bandwidth, int gpus) {
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
}

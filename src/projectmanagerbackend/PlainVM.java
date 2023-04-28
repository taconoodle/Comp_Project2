package projectmanagerbackend;

import globals.Globals.OperatingSystems;

public class PlainVM extends VM {
    private double vmDiskSpace;
    protected PlainVM(int id, int cores, double ram, OperatingSystems os, double diskSpace) {
        super(id, cores, ram, os);
        vmDiskSpace = diskSpace;
    }

    @Override
    public double getVmDiskSpace() {
        return vmDiskSpace;
    }


    @Override
    public void setVmDiskSpace(double vmDiskSpace) {
        this.vmDiskSpace = vmDiskSpace;
    }

    @Override
    protected int getVmGPUs() {
        return 0;
    }

    @Override
    protected void setVmGPUs(int vmGPUs) {

    }

    @Override
    protected double getVmBandwidth() {
        return 0;
    }

    @Override
    protected void setVmBandwidth(double vmBandwidth) {

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
            super.setVmOS((OperatingSystems) parameters[2]);  //third parameter should be the updated OS
        }
        if(parameters[3] != null) {
            vmDiskSpace = (double) parameters[3];   //fourth parameter should be the updated disk space in the SSD allocated to the VM
        }
    }

    @Override
    protected String displayResources() {
        return "\tVM ID: " + getVmId() + "\tVM Cores: " + getVmCores() + "\tVM RAM: " + getVmRam() + " GB" + "\tVM Disk Space: " + vmDiskSpace + " GB";
    }
}

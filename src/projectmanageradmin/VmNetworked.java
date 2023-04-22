package projectmanageradmin;

import globals.Globals;

public class VmNetworked extends BaseVM{
    protected VmNetworked(int id, int cores, double ram, Globals.OperatingSystems os, double diskSpace, double bandwidth) {
        super(id, cores, ram, os);
        super.setVmDiskSpace(diskSpace);
        super.setVmBandwidth(bandwidth);
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
    }

    @Override
    protected String displayResources() {
        return getVmId() + "\t" + getVmCores() + "\t" + getVmRam() + "\t" + getVmDiskSpace() + "\t" + getVmBandwidth();
    }
}

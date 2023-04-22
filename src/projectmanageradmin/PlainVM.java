package projectmanageradmin;

import globals.Globals.OperatingSystems;

public class PlainVM extends BaseVM{
    protected PlainVM(int id, int cores, double ram, OperatingSystems os, double diskSpace) {
        super(id, cores, ram, os);
        super.setVmDiskSpace(diskSpace);
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
            super.setVmDiskSpace((double) parameters[3]);   //fourth parameter should be the updated disk space in the SSD allocated to the VM
        }
    }

    @Override
    protected String displayResources() {
        return getVmId() + "\t" + getVmCores() + "\t" + getVmRam() + "\t" + getVmDiskSpace();
    }
}

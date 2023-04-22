package projectManagerAdmin;

import globals.Globals.OperatingSystems;

public class PlainVM extends BaseVM{
    private PlainVM(int id, int cores, float ram, OperatingSystems os, float diskSpace) {
        super(id, cores, ram, os);
        super.setVmDiskSpace(diskSpace);
    }

    @Override
    protected void updateVM (Object... parameters) {
        super.setVmCores( (int) parameters[0] ); //first parameter should be the updated amount of cores
        super.setVmRam( (float) parameters[1] );    //second parameter should be the updated amount of ram
        super.setVmOS( (OperatingSystems) parameters[2] );  //third parameter should be the updated OS
        super.setVmDiskSpace( (float) parameters[3] );   //fourth parameter should be the updated disk space in the SSD allocated to the VM
    }
}

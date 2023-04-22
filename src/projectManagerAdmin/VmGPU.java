package projectManagerAdmin;

import globals.Globals.OperatingSystems;

public class VmGPU extends BaseVM{
    private VmGPU (int id, int cores, float ram, OperatingSystems os, float diskSpace, int gpus) {
        super(id, cores, ram, os);
        super.setVmDiskSpace(diskSpace);
        super.setVmGPUs(gpus);
    }

    @Override
    protected void updateVM(Object... parameters) {
        super.setVmCores( (int) parameters[0]); //first parameter should be the updated amount of CPU cores
        super.setVmRam( (float) parameters[1]);    //second parameter should be the updated amount of RAM
        super.setVmOS( (OperatingSystems) parameters[2]);  //third parameter should be the updated OS
        super.setVmDiskSpace( (float) parameters[3] );   //fourth parameter should be the updated disk space in the SSD allocated to the VM
        super.setVmGPUs( (int) parameters[4] ); //fifth parameter should be the updated amount of GPUs
    }
}

package projectManagerAdmin;

import globals.Globals;

public class VmNetworkedGPU extends BaseVM{
    private VmNetworkedGPU(int id, int cores, float ram, Globals.OperatingSystems os, float diskSpace, float bandwidth, int gpus) {
        super(id, cores, ram, os);
        super.setVmDiskSpace(diskSpace);
        super.setVmBandwidth(bandwidth);
        super.setVmGPUs(gpus);
    }

    @Override
    protected void updateVM (Object... parameters) {
        super.setVmCores( (int) parameters[0] ); //first parameter should be the updated amount of cores
        super.setVmRam( (float) parameters[1] );    //second parameter should be the updated amount of ram
        super.setVmOS( (Globals.OperatingSystems) parameters[2] );  //third parameter should be the updated OS
        super.setVmDiskSpace( (float) parameters[3] );   //fourth parameter should be the updated disk space in the SSD allocated to the VM
        super.setVmBandwidth( (float) parameters[4] );   //fifth parameter should be the updated amount of bandwidth
        super.setVmGPUs( (int) parameters[5] ); //sixth parameter should be the updated amount of GPUs
    }
}

package projectmanagerbackend;

import globals.Globals;

public class VmNetworkedGUI extends PlainVMGUI {
    private double vmBandwidth;
    private double allocatedBandwidth;

    protected VmNetworkedGUI(int id, int cores, double ram, Globals.OperatingSystems os, double diskSpace, double bandwidth) {
        super(id, cores, ram, os, diskSpace);
        vmBandwidth = bandwidth;
    }

    @Override
    public double getVmBandwidth() {
        return vmBandwidth;
    }

    @Override
    public void setVmBandwidth(double vmBandwidth) {
        this.vmBandwidth = vmBandwidth;
    }

    @Override
    public double getAllocatedBandwidth() {
        return allocatedBandwidth;
    }

    @Override
    public void setAllocatedBandwidth(double allocatedBandwidth) {
        this.allocatedBandwidth = allocatedBandwidth;
    }

    @Override
    protected void updateVM(Object... parameters) {
        if (parameters[0] != null) {
            super.setVmCores((int) parameters[0]); //first parameter should be the updated amount of cores
        }
        if (parameters[1] != null) {
            super.setVmRam((double) parameters[1]);    //second parameter should be the updated amount of ram
        }
        if (parameters[2] != null) {
            super.setVmOS((Globals.OperatingSystems) parameters[2]);  //third parameter should be the updated OS
        }
        if (parameters[3] != null) {
            super.setVmDiskSpace((double) parameters[3]);   //fourth parameter should be the updated disk space in the SSD allocated to the VM
        }
        if (parameters[4] != null) {
            vmBandwidth = (double) parameters[4];   //fifth parameter should be the updated amount of bandwidth
        }
    }

    @Override
    protected String displayResources() {
        return super.displayResources() + "\tVM Bandwidth: " + vmBandwidth + " GB/s";
    }

    @Override
    protected double calculateVmLoad() {
        double vmLoad = super.calculateVmLoad();
        if (getAllocatedBandwidth() != 0) {
            vmLoad += (getAllocatedBandwidth() / getVmBandwidth());
        }
        return vmLoad / 4;
    }

    @Override
    protected void updateVmResources(String mode) {
        switch(mode) {
            case "commit":
                super.updateVmResources("commit");
                vmBandwidth -= allocatedBandwidth;
                break;
            case "release":
                super.updateVmResources("release");
                vmBandwidth += allocatedBandwidth;
                break;
        }
    }

    @Override
    protected void startWorkingOnProgram(ProgramGUI prog) {
        allocatedBandwidth -= prog.getPBandwidth();
        super.startWorkingOnProgram(prog);
    }
    @Override
    protected void calcLoadAfterAssigningProgram(ProgramGUI prog) {
        allocatedBandwidth -= prog.getPBandwidth();
        super.calcLoadAfterAssigningProgram(prog);
    }

    @Override
    protected void stopWorkingOnProgram(ProgramGUI prog) {
        allocatedBandwidth += prog.getPBandwidth();
        super.stopWorkingOnProgram(prog);
    }
}

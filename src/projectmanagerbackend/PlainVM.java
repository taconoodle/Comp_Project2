package projectmanagerbackend;

import globals.Globals.OperatingSystems;

public class PlainVM extends VM {
    private double vmDiskSpace;
    private double allocatedDiskSpace;

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
    public double getAllocatedDiskSpace() {
        return allocatedDiskSpace;
    }

    @Override
    public void setAllocatedDiskSpace(double allocatedDiskSpace) {
        this.allocatedDiskSpace = allocatedDiskSpace;
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
            super.setVmOS((OperatingSystems) parameters[2]);  //third parameter should be the updated OS
        }
        if (parameters[3] != null) {
            vmDiskSpace = (double) parameters[3];   //fourth parameter should be the updated disk space in the SSD allocated to the VM
        }
    }

    @Override
    protected String displayResources() {
        return "\tVM ID: " + getVmId() + "\tVM Cores: " + getVmCores() + "\tVM RAM: " + getVmRam() + " GB" + "\tVM Disk Space: " + vmDiskSpace + " GB";
    }

    @Override
    protected double calculateVmLoad() {
        double vmLoad = ((double) getAllocatedCores() / (double) getVmCores()) +
                (getAllocatedRam() / getVmRam()) +
                (getAllocatedDiskSpace() / getVmDiskSpace());
        return vmLoad / 3;
    }

    @Override
    protected void startWorkingOnProgram(Program prog) {
        allocatedDiskSpace += prog.getPDiskSpace();

        vmDiskSpace -= prog.getPDiskSpace();
        super.startWorkingOnProgram(prog);
    }

    @Override
    protected void stopWorkingOnProgram(Program prog) {
        allocatedDiskSpace -= prog.getPDiskSpace();

        vmDiskSpace += prog.getPDiskSpace();
        super.stopWorkingOnProgram(prog);
    }

    @Override
    protected double calculateLoadAfterProgAssignement(Program prog) {
        double vmLoad = (((double) getAllocatedCores() + (double) prog.getPCores()) / (double) getVmCores()) +
                ((getAllocatedRam() + prog.getPRam()) / getVmRam()) +
                ((getAllocatedDiskSpace() + prog.getPDiskSpace()) / getVmDiskSpace());
        return vmLoad / 3;
    }
}
package projectmanageradmin;

import globals.Globals.OperatingSystems;

public class VM extends BaseVM {
    protected VM(int id, int cores, double ram, OperatingSystems os) {
        super(id, cores, ram, os);
    }

    @Override
    protected void updateVM(Object... parameters) {
        if(parameters[0] != null){
            super.setVmCores( (int) parameters[0]); //first parameter should be the updated amount of cores
        }
        if(parameters[1] != null) {
            super.setVmRam((double) parameters[1]);    //second parameter should be the updated amount of ram
        }
        if(parameters[2] != null) {
            super.setVmOS((OperatingSystems) parameters[2]);  //third parameter should be the updated OS
        }
    }

    @Override
    protected String displayResources() {
        return getVmId() + "\t" + getVmCores() + "\t" + getVmRam();
    }
}

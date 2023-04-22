package projectManagerAdmin;

import globals.Globals.OperatingSystems;

public class VM extends BaseVM {
    protected VM(int id, int cores, float ram, OperatingSystems os) {
        super(id, cores, ram, os);
    }

    @Override
    protected void updateVM(Object... parameters) {
        super.setVmCores( (int) parameters[0]); //first parameter should be the updated amount of cores
        super.setVmRam( (float) parameters[1]);    //second parameter should be the updated amount of ram
        super.setVmOS( (OperatingSystems) parameters[2]);  //third parameter should be the updated OS
    }
}

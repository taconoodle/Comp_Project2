package projectmanagerbackend;

public class ProgramManager {
    private Cluster cluster;

    protected ProgramManager(Cluster cluster) {
        this.cluster = cluster;
    }

    protected boolean createProgram(int cores, int ram, int diskSpace, int gpu, int bandwidth, int expectedTime) {
        ResourceManager resMger = new ResourceManager(cluster);
        double[] totalResources = resMger.calculateTotalResources();
        if ((cores <= 0 || cores > totalResources[0]) || (ram <= 0 || ram > totalResources[1]) || (diskSpace < 0 || diskSpace > totalResources[2]) || (gpu < 0 || gpu > totalResources[3]) ||
                (bandwidth < 0 || bandwidth > totalResources[4]) || expectedTime <= 0) {
            System.out.println("\nSystem error: Invalid values or not enough VMs to support to execute the program.");
            return false;
        }
        Program newProg = new Program(cores, ram, diskSpace, gpu, bandwidth, expectedTime, calculateProgramPriority(totalResources, cores, ram, diskSpace, gpu, bandwidth));
        cluster.getMyProgs().add(newProg);
        cluster.setNumOfProgs(cluster.getNumOfProgs() + 1);
        System.out.println("\nSuccessfully added new Program with ID: " + newProg.getPID() + ".");
        return true;
    }

    protected double calculateProgramPriority(double[] resources, int cores, int ram, int diskSpace, int gpu, int bandwidth) {
        double priority = ((cores / resources[0]) + (ram / resources[1]));
        if (resources[2] != 0) {
            priority += diskSpace / resources[2];
        }
        if (resources[3] != 0) {
            priority += gpu / resources[3];
        }
        if (resources[4] != 0) {
            priority += bandwidth / resources[4];
        }
        return priority;
    }
}

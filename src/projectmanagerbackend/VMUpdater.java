package projectmanagerbackend;

import static globals.Globals.MIN_BANDWIDTH_PER_VM;

public class VMUpdater {

    private Cluster cluster;
    private ResourceManager resMag;

    public VMUpdater (Cluster cluster) {
        this.cluster = cluster;
        resMag = new ResourceManager(this.cluster);
    }

    protected void updatePlainVM(int vmID, int cores, double ram, String os, double diskSpace) {
        if ((cores <= 0 || cores > cluster.getAvailableCPU()) || (ram <= 0 || ram > cluster.getAvailableRAM()) || (diskSpace <= 0 || diskSpace > cluster.getAvailableDiskSpace()) || cluster.osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID " + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        resMag.freeResources(cluster.getMyVMs().get(vmID).getVmCores(), cluster.getMyVMs().get(vmID).getVmRam(), cluster.getMyVMs().get(vmID).getVmDiskSpace());
        cluster.getMyVMs().get(vmID).updateVM(cores, ram, cluster.getOS(os), diskSpace);
        resMag.allocateResources(cluster.getMyVMs().get(vmID).getVmCores(), cluster.getMyVMs().get(vmID).getVmRam(), cluster.getMyVMs().get(vmID).getVmDiskSpace());
        System.out.println("Successfully updated VM.");
    }

    protected void updateVmGPU(int vmID, int cores, double ram, String os, double diskSpace, int gpus) {
        if ((cores <= 0 || cores > cluster.getAvailableCPU()) || (ram <= 0 || ram > cluster.getAvailableRAM()) || (diskSpace <= 0 || diskSpace > cluster.getAvailableDiskSpace()) ||
                (gpus <= 0 || gpus > cluster.getAvailableGPU()) || cluster.osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID " + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        resMag.freeResources(cluster.getMyVMs().get(vmID).getVmCores(), cluster.getMyVMs().get(vmID).getVmRam(), cluster.getMyVMs().get(vmID).getVmDiskSpace(), cluster.getMyVMs().get(vmID).getVmGPUs());
        cluster.getMyVMs().get(vmID).updateVM(cores, ram, cluster.getOS(os), diskSpace, gpus);
        resMag.allocateResources(cluster.getMyVMs().get(vmID).getVmCores(), cluster.getMyVMs().get(vmID).getVmRam(), cluster.getMyVMs().get(vmID).getVmDiskSpace(), cluster.getMyVMs().get(vmID).getVmGPUs());
        System.out.println("Successfully updated VM.");
    }

    protected void updateVmNetworked(int vmID, int cores, double ram, String os, double diskSpace, double bandwidth) {
        if ((cores <= 0 || cores > cluster.getAvailableCPU()) || (ram <= 0 || ram > cluster.getAvailableRAM()) || (diskSpace <= 0 || diskSpace > cluster.getAvailableDiskSpace()) ||
                (bandwidth <= MIN_BANDWIDTH_PER_VM || bandwidth > cluster.getAvailableBandwidth()) || cluster.osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID " + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        resMag.freeResources(cluster.getMyVMs().get(vmID).getVmCores(), cluster.getMyVMs().get(vmID).getVmRam(), cluster.getMyVMs().get(vmID).getVmDiskSpace(), cluster.getMyVMs().get(vmID).getVmBandwidth());
        cluster.getMyVMs().get(vmID).updateVM(cores, ram, cluster.getOS(os), diskSpace, bandwidth);
        resMag.allocateResources(cluster.getMyVMs().get(vmID).getVmCores(), cluster.getMyVMs().get(vmID).getVmRam(), cluster.getMyVMs().get(vmID).getVmDiskSpace(), cluster.getMyVMs().get(vmID).getVmBandwidth());
        System.out.println("Successfully updated VM.");
    }

    protected void updateVmNetworkedGPU(int vmID, int cores, double ram, String os, double diskSpace, double bandwidth, int gpus) {
        if ((cores <= 0 || cores > cluster.getAvailableCPU()) || (ram <= 0 || ram > cluster.getAvailableRAM()) || (diskSpace <= 0 || diskSpace > cluster.getAvailableDiskSpace()) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > cluster.getAvailableBandwidth()) || (gpus <= 0 || gpus > cluster.getAvailableGPU()) || cluster.osExists(os) == -1) {
            System.out.println("Error while trying to update VM with ID " + vmID + ". Values given are not valid or there are not enough resources.");
            return;
        }
        resMag.freeResources(cluster.getMyVMs().get(vmID).getVmCores(), cluster.getMyVMs().get(vmID).getVmRam(), cluster.getMyVMs().get(vmID).getVmDiskSpace(), cluster.getMyVMs().get(vmID).getVmBandwidth(), cluster.getMyVMs().get(vmID).getVmGPUs());
        cluster.getMyVMs().get(vmID).updateVM(cores, ram, cluster.getOS(os), diskSpace, bandwidth, gpus);
        resMag.allocateResources(cluster.getMyVMs().get(vmID).getVmCores(), cluster.getMyVMs().get(vmID).getVmRam(), cluster.getMyVMs().get(vmID).getVmDiskSpace(), cluster.getMyVMs().get(vmID).getVmBandwidth(), cluster.getMyVMs().get(vmID).getVmGPUs());
        System.out.println("Successfully updated VM.");
    }
}

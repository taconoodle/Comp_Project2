package projectmanagerbackend;

import static globals.Globals.MIN_BANDWIDTH_PER_VM;

public class VMCreator {

    private Cluster cluster;
    private ResourceManager resMag;

    protected VMCreator(Cluster cluster) {
        this.cluster = cluster;
        resMag = new ResourceManager(this.cluster);
    }

    protected boolean createPlainVm(int cores, double ram, String os, double diskSpace) {
        if ((cores <= 0 || cores > cluster.getAvailableCPU()) || (ram <= 0 || ram > cluster.getAvailableRAM()) || cluster.osExists(os) == -1 || (diskSpace <= 0 || diskSpace > cluster.getAvailableDiskSpace())) {
            System.out.println("System error: -1. Wrong values, not enough resources or OS not supported.");
            return false;
        }
        PlainVM newVM = new PlainVM(cluster.getVmIdCount(), cores, ram, cluster.getOS(os), diskSpace);
        cluster.getMyVMs().add(newVM);
        cluster.setNumOfVMs(cluster.getNumOfVMs() + 1);
        resMag.allocateResources(cores, ram, diskSpace);
        System.out.println("Successfully added new Plain VM with ID " + cluster.getVmIdCount() + ".");
        cluster.setVmIdCount(cluster.getVmIdCount() + 1);
        return true;
    }

    protected boolean createVmGPU(int cores, double ram, String os, double diskSpace, int gpus) {
        if ((cores <= 0 || cores > cluster.getAvailableCPU()) || (ram <= 0 || ram > cluster.getAvailableRAM()) || cluster.osExists(os) == -1 || (diskSpace <= 0 || diskSpace > cluster.getAvailableDiskSpace()) ||
                (gpus <= 0 || gpus > cluster.getAvailableGPU())) {
            System.out.println("System error: -1. Wrong values, not enough resources or OS not supported.");
            return false;
        }
        VmGPU newVM = new VmGPU(cluster.getVmIdCount(), cores, ram, cluster.getOS(os), diskSpace, gpus);
        cluster.getMyVMs().add(newVM);
        cluster.setNumOfVMs(cluster.getNumOfVMs() + 1);
        resMag.allocateResources(cores, ram, diskSpace, gpus);
        System.out.println("Successfully added new VM with GPU with ID " + cluster.getVmIdCount() + ".");
        cluster.setVmIdCount(cluster.getVmIdCount() + 1);
        return true;
    }

    protected boolean createVmNetworked(int cores, double ram, String os, double diskSpace, double bandwidth) {
        if ((cores <= 0 || cores > cluster.getAvailableCPU()) || (ram <= 0 || ram > cluster.getAvailableRAM()) || cluster.osExists(os) == -1 || (diskSpace <= 0 || diskSpace > cluster.getAvailableDiskSpace()) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > cluster.getAvailableBandwidth())) {
            System.out.println("System error: -1. Wrong values, not enough resources or OS not supported.");
            return false;
        }
        VmNetworked newVM = new VmNetworked(cluster.getVmIdCount(), cores, ram, cluster.getOS(os), diskSpace, bandwidth);
        cluster.getMyVMs().add(newVM);
        cluster.setNumOfVMs(cluster.getNumOfVMs() + 1);
        resMag.allocateResources(cores, ram, diskSpace, bandwidth);
        System.out.println("Successfully added new networked VM with ID " + cluster.getVmIdCount() + ".");
        cluster.setVmIdCount(cluster.getVmIdCount() + 1);
        return true;
    }

    protected boolean createVmNetworkedGPU(int cores, double ram, String os, double diskSpace, double bandwidth, int gpus) {
        if ((cores <= 0 || cores > cluster.getAvailableCPU()) || (ram <= 0 || ram > cluster.getAvailableRAM()) || cluster.osExists(os) == -1 || (diskSpace <= 0 || diskSpace > cluster.getAvailableDiskSpace()) ||
                (bandwidth < MIN_BANDWIDTH_PER_VM || bandwidth > cluster.getAvailableBandwidth()) || (gpus <= 0 || gpus > cluster.getAvailableGPU())) {
            System.out.println("System error: -1. Wrong values, not enough resources or OS not supported.");
            return false;
        }
        VmNetworkedGPU newVM = new VmNetworkedGPU(cluster.getVmIdCount(), cores, ram, cluster.getOS(os), diskSpace, bandwidth, gpus);
        cluster.getMyVMs().add(newVM);
        cluster.setNumOfVMs(cluster.getNumOfVMs() + 1);
        resMag.allocateResources(cores, ram, diskSpace, bandwidth, gpus);
        System.out.println("Successfully added new networked VM with GPU with ID " + cluster.getVmIdCount() + ".");
        cluster.setVmIdCount(cluster.getVmIdCount() + 1);
        return true;
    }
}

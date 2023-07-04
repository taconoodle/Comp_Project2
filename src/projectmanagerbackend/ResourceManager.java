package projectmanagerbackend;

import static globals.Globals.*;

public class ResourceManager {
    private Cluster cluster;

    protected ResourceManager (Cluster cluster) {
        this.cluster = cluster;
    }

    protected void allocateResources(int cores, double ram, double diskSpace) {
        cluster.setAvailableCPU(cluster.getAvailableCPU() - cores);
        cluster.setAvailableRAM(cluster.getAvailableRAM() - ram);
        cluster.setAvailableDiskSpace(cluster.getAvailableDiskSpace() - diskSpace);
    }

    protected void allocateResources(int cores, double ram, double diskSpace, int gpus) {
        cluster.setAvailableCPU(cluster.getAvailableCPU() - cores);
        cluster.setAvailableRAM(cluster.getAvailableRAM() - ram);
        cluster.setAvailableDiskSpace(cluster.getAvailableDiskSpace() - diskSpace);
        cluster.setAvailableGPU(cluster.getAvailableGPU() - gpus);
    }

    protected void allocateResources(int cores, double ram, double diskSpace, double bandwidth) {
        cluster.setAvailableCPU(cluster.getAvailableCPU() - cores);
        cluster.setAvailableRAM(cluster.getAvailableRAM() - ram);
        cluster.setAvailableDiskSpace(cluster.getAvailableDiskSpace() - diskSpace);
        cluster.setAvailableBandwidth(cluster.getAvailableBandwidth() - bandwidth);
    }

    protected void allocateResources(int cores, double ram, double diskSpace, double bandwidth, int gpus) {
        cluster.setAvailableCPU(cluster.getAvailableCPU() - cores);
        cluster.setAvailableRAM(cluster.getAvailableRAM() - ram);
        cluster.setAvailableDiskSpace(cluster.getAvailableDiskSpace() - diskSpace);
        cluster.setAvailableBandwidth(cluster.getAvailableBandwidth() - bandwidth);
        cluster.setAvailableGPU(cluster.getAvailableGPU() - gpus);
    }

    protected void freeResources(int cores, double ram, double diskSpace) {
        cluster.setAvailableCPU(cluster.getAvailableCPU() + cores);
        cluster.setAvailableRAM(cluster.getAvailableRAM() + ram);
        cluster.setAvailableDiskSpace(cluster.getAvailableDiskSpace() + diskSpace);
    }

    protected void freeResources(int cores, double ram, double diskSpace, int gpus) {
        cluster.setAvailableCPU(cluster.getAvailableCPU() + cores);
        cluster.setAvailableRAM(cluster.getAvailableRAM() + ram);
        cluster.setAvailableDiskSpace(cluster.getAvailableDiskSpace() + diskSpace);
        cluster.setAvailableGPU(cluster.getAvailableGPU() + gpus);
    }

    protected void freeResources(int cores, double ram, double diskSpace, double bandwidth) {
        cluster.setAvailableCPU(cluster.getAvailableCPU() + cores);
        cluster.setAvailableRAM(cluster.getAvailableRAM() + ram);
        cluster.setAvailableDiskSpace(cluster.getAvailableDiskSpace() + diskSpace);
        cluster.setAvailableBandwidth(cluster.getAvailableBandwidth() + bandwidth);
    }

    protected void freeResources(int cores, double ram, double diskSpace, double bandwidth, int gpus) {
        cluster.setAvailableCPU(cluster.getAvailableCPU() + cores);
        cluster.setAvailableRAM(cluster.getAvailableRAM() + ram);
        cluster.setAvailableDiskSpace(cluster.getAvailableDiskSpace() + diskSpace);
        cluster.setAvailableBandwidth(cluster.getAvailableBandwidth() + bandwidth);
        cluster.setAvailableGPU(cluster.getAvailableGPU() + gpus);
    }

    protected double[] calculateTotalResources() {
        double[] totalResources = new double[5];
        totalResources[0] = AMOUNT_OF_CPU - cluster.getAvailableCPU();
        totalResources[1] = AMOUNT_OF_RAM - cluster.getAvailableRAM();
        totalResources[2] = AMOUNT_OF_DISK_SPACE - cluster.getAvailableDiskSpace();
        totalResources[3] = AMOUNT_OF_GPU - cluster.getAvailableGPU();
        totalResources[4] = NUM_OF_BANDWIDTH - cluster.getAvailableBandwidth();
        return totalResources;
    }
}

package projectmanagerbackend;

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
}

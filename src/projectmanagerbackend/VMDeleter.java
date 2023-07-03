package projectmanagerbackend;

public class VMDeleter {
    private Cluster cluster;
    private ResourceManager resMag;

    protected VMDeleter (Cluster cluster) {
        this.cluster = cluster;
        resMag = new ResourceManager(this.cluster);
    }

    protected void deletePlainVM(int vmID) {
        if (cluster.findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");
            return;
        }
        resMag.freeResources(cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmCores(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmRam(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmDiskSpace());
        cluster.getMyVMs().remove(cluster.getMyVMs().get(cluster.findVmById(vmID)));
        cluster.setNumOfVMs(cluster.getNumOfVMs() - 1);
        System.out.println("VM successfully deleted.");
    }

    protected void deleteVmGPU(int vmID) {
        if (cluster.findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");
            return;
        }
        resMag.freeResources(cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmCores(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmRam(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmDiskSpace(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmGPUs());
        cluster.getMyVMs().remove(cluster.getMyVMs().get(cluster.findVmById(vmID)));
        cluster.setNumOfVMs(cluster.getNumOfVMs() - 1);
        System.out.println("VM successfully deleted.");
    }

    protected void deleteVmNetworked(int vmID) {
        if (cluster.findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");
            return;
        }
        resMag.freeResources(cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmCores(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmRam(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmDiskSpace(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmBandwidth());
        cluster.getMyVMs().remove(cluster.getMyVMs().get(cluster.findVmById(vmID)));
        cluster.setNumOfVMs(cluster.getNumOfVMs() - 1);
        System.out.println("VM successfully deleted.");
    }

    protected void deleteVmNetworkedGPU(int vmID) {
        if (cluster.findVmById(vmID) == -1) {
            System.out.println("VM deletion failed. No VM with such ID exists.");
            return;
        }
        resMag.freeResources(cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmCores(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmRam(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmDiskSpace(),
                cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmBandwidth(), cluster.getMyVMs().get(cluster.findVmById(vmID)).getVmGPUs());
        cluster.getMyVMs().remove(cluster.getMyVMs().get(cluster.findVmById(vmID)));
        cluster.setNumOfVMs(cluster.getNumOfVMs() - 1);
        System.out.println("VM successfully deleted.");
    }
}

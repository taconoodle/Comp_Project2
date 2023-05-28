package globals;

public class Globals {
    public static final int AMOUNT_OF_CPU = 128;   //the amount of CPU cores the cluster has, free or not
    public static final float AMOUNT_OF_RAM = 256; //the amount of RAM in GBs the cluster has, free or not
    public static final float AMOUNT_OF_DISK_SPACE = 2048;  //the amount of SSD disk space in GBs the cluster has, free or not
    public static final int AMOUNT_OF_GPU = 8; //the amount of GPUs the cluster has, free or not
    public static final float NUM_OF_BANDWIDTH = 320;   //the amount of internet bandwidth the cluster can utilize, used or not

    public enum OperatingSystems {   //the supported operating systems
        WINDOWS,
        UBUNTU,
        FEDORA
    }

    public static final float MIN_BANDWIDTH_PER_VM = 4;   //the minimum amount of bandwidth the VM should get
    public static final int MAX_ASSIGNMENT_ATTEMPTS = 3;

    public static final String PROGRAM_INIT_TEXT = "Cluster initialized.\n\nThere are 128 CPU cores, 256 GB RAM, 2048 GB of SSD space, 8 GPUs and 320 GB/s Internet Bandwidth available.\n" +
            "The supported OSes are Windows, Ubuntu and Fedora.\n";

    public static final String PROGRAM_INIT_TEXT_GUI = "<html><p>Cluster initialized.<br/>There are 128 CPU cores, 256 GB RAM, 2048 GB of SSD space, 8 GPUs and 320 GB/s Internet Bandwidth available.<br/>The supported OSes are Windows, Ubuntu and Fedora.</p></html>";
}
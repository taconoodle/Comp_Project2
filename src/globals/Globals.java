package globals;

public class Globals {
    public static int AMOUNT_OF_CPU = 128;   //the amount of CPU cores the cluster has, free or not
    public static float AMOUNT_OF_RAM = 256; //the amount of RAM in GBs the cluster has, free or not
    public static float AMOUNT_OF_DISK_SPACE = 2048;  //the amount of SSD disk space in GBs the cluster has, free or not
    public static int AMOUNT_OF_GPU = 8; //the amount of GPUs the cluster has, free or not
    public static float NUM_OF_BANDWIDTH = 320;   //the amount of internet bandwidth the cluster can utilize, used or not
    public static enum OperatingSystems {
        WINDOWS,
        UBUNTU,
        FEDORA
    }
    public static float MIN_BANDWIDTH_PER_VM = 4;
}

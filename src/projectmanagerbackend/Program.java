package projectmanagerbackend;

import java.util.Random;

public class Program {
    private int pID;
    private int pCores;
    private int pRam;
    private int pDiskSpace;
    private int pGpu;
    private int pBandwidth;
    private int pExpectedTime;
    //private int pExecTime;
    //private int pStartExecTime;
    //private int currentExecTime;
    private double pPriority;

    protected Program(int cores, int ram, int diskSpace, int gpu, int bandwidth, int expectedTime, double priority) {
        Random newRandom = new Random();
        pID = newRandom.nextInt(Integer.MAX_VALUE) + 1;
        pCores = cores;
        pRam = ram;
        pDiskSpace = diskSpace;
        pGpu = gpu;
        pBandwidth = bandwidth;
        pExpectedTime = expectedTime;
        //pExecTime = 0;
        //pStartExecTime = 0;
        //currentExecTime = 0;
        pPriority = priority;
    }//never going home

    protected int getPID() {
        return pID;
    }

    private void setPID(int pID) {
        this.pID = pID;
    }

    private int getPCores() {
        return pCores;
    }

    private void setPCores(int pCores) {
        this.pCores = pCores;
    }

    private int getPRam() {
        return pRam;
    }

    private void setPRam(int pRam) {
        this.pRam = pRam;
    }

    private int getPDiskSpace() {
        return pDiskSpace;
    }

    private void setPDiskSpace(int pDiskSpace) {
        this.pDiskSpace = pDiskSpace;
    }

    private int getPGpu() {
        return pGpu;
    }

    private void setPGpu(int pGpu) {
        this.pGpu = pGpu;
    }

    private int getPBandwidth() {
        return pBandwidth;
    }

    private void setPBandwidth(int pBandwidth) {
        this.pBandwidth = pBandwidth;
    }

    private int getPExpectedTime() {
        return pExpectedTime;
    }

    private void setPExpectedTime(int pExpectedTime) {
        this.pExpectedTime = pExpectedTime;
    }

    protected double getPPriority() {
        return pPriority;
    }

    private void setPPriority(int pPriority) {
        this.pPriority = pPriority;
    }
}

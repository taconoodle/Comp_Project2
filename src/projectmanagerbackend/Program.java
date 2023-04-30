package projectmanagerbackend;

import java.io.Serializable;
import java.util.Random;

import static globals.Globals.AMOUNT_OF_CPU;

public class Program implements Serializable {
    private int pID;
    private int pCores;
    private int pRam;
    private int pDiskSpace;
    private int pGpu;
    private int pBandwidth;
    private long pExpectedTime;
    private long pExecTime;
    private long pStartExecTime;
    private long currentExecTime;
    private double pPriority;
    private int assignAttempts;

    protected Program(int cores, int ram, int diskSpace, int gpu, int bandwidth, int expectedTime, double priority) {
        Random newRandom = new Random();
        pID = newRandom.nextInt(AMOUNT_OF_CPU) + 1;
        pCores = cores;
        pRam = ram;
        pDiskSpace = diskSpace;
        pGpu = gpu;
        pBandwidth = bandwidth;
        pExpectedTime = expectedTime;
        pExecTime = 0;
        pStartExecTime = 0;
        currentExecTime = 0;
        pPriority = priority;
        assignAttempts = 0;
    }//never going home

    protected int getPID() {
        return pID;
    }

    private void setPID(int pID) {
        this.pID = pID;
    }

    protected int getPCores() {
        return pCores;
    }

    private void setPCores(int pCores) {
        this.pCores = pCores;
    }

    protected int getPRam() {
        return pRam;
    }

    private void setPRam(int pRam) {
        this.pRam = pRam;
    }

    protected int getPDiskSpace() {
        return pDiskSpace;
    }

    private void setPDiskSpace(int pDiskSpace) {
        this.pDiskSpace = pDiskSpace;
    }

    protected int getPGpu() {
        return pGpu;
    }

    private void setPGpu(int pGpu) {
        this.pGpu = pGpu;
    }

    protected int getPBandwidth() {
        return pBandwidth;
    }

    private void setPBandwidth(int pBandwidth) {
        this.pBandwidth = pBandwidth;
    }

    protected long getPExpectedTime() {
        return pExpectedTime;
    }


    protected double getPPriority() {
        return pPriority;
    }

    private void setPPriority(double pPriority) {
        this.pPriority = pPriority;
    }


    public void setPExpectedTime(long pExpectedTime) {
        this.pExpectedTime = pExpectedTime;
    }

    protected long getPExecTime() {
        return pExecTime;
    }

    public void setPExecTime(long pExecTime) {
        this.pExecTime = pExecTime;
    }

    protected long getPStartExecTime() {
        return pStartExecTime;
    }

    public void setPStartExecTime(long pStartExecTime) {
        this.pStartExecTime = pStartExecTime;
    }

    protected long getCurrentExecTime() {
        return currentExecTime;
    }

    public void setCurrentExecTime(long currentExecTime) {
        this.currentExecTime = currentExecTime;
    }

    public int getAssignAttempts() {
        return assignAttempts;
    }

    public void setAssignAttempts(int assignAttempts) {
        this.assignAttempts = assignAttempts;
    }
}

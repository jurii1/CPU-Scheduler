package com.main;
import java.util.Comparator;

/**
 *
 * @author Juri
 */
public class Process {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int turnaroundTime;
    private int cpuBurst;
    private int ioBurst;
    private boolean cpuBound;
    private int startTime;
    private int endTime;
    
    public Process(String name, int arrivalTime, int burstTime, boolean cpuBound) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = burstTime;
        this.turnaroundTime = arrivalTime;
        this.cpuBurst = burstTime;
        this.cpuBound = cpuBound;
    }

    public Process(String name, int arrivalTime, int burstTime, int cpuBurst, int ioBurst) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = burstTime;
        this.turnaroundTime = arrivalTime;
        this.cpuBurst = cpuBurst;
        this.ioBurst = ioBurst;
    }

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = burstTime;
        this.turnaroundTime = arrivalTime;
    }
    
    public Process(int startTime, String name, int endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getCpuBurst() {
        return cpuBurst;
    }

    public void setCpuBurst(int cpuBurst) {
        this.cpuBurst = cpuBurst;
    }

    public int getIoBurst() {
        return ioBurst;
    }

    public void setIoBurst(int ioBurst) {
        this.ioBurst = ioBurst;
    }

    public boolean isCpuBound() {
        return cpuBound;
    }
    
    //Comparator for sorting the list by Arrival Time
    public static Comparator<Process> processArrivalTime = new Comparator<Process>() {
        public int compare(Process p1, Process p2) {

            int AT1 = p1.getArrivalTime();
            int AT2 = p2.getArrivalTime();

            return AT1-AT2;
        }
    };
    
    public static Comparator<Process> processBurstTime = new Comparator<Process>() {
        public int compare(Process p1, Process p2) {

            int BT1 = p1.getBurstTime();
            int BT2 = p2.getBurstTime();

            return BT1-BT2;
        }
    };
    
    //Comparator for sorting the list by burstTime or CPU burst (SJF); defining which will be executed. This will be used in the execution List
    public static Comparator<Process> processSJF_IO = new Comparator<Process>() {
	public int compare(Process p1, Process p2) {
            
            int comparator1, comparator2;
            
            if(p1.getCpuBurst() <= p1.getBurstTime()){
                comparator1 = p1.getCpuBurst();
            }else{
                comparator1 = p1.getBurstTime();
            }
            
            if(p2.getCpuBurst() <= p2.getBurstTime()){
                comparator2 = p2.getCpuBurst();
            }else{
                comparator2 = p2.getBurstTime();
            }

            return comparator1-comparator2;
        }
    };

    @Override
    public String toString() {
        return "Process{" + "name=" + name + ", waitingTime=" + waitingTime + ", turnaroundTime=" + turnaroundTime + '}';
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}

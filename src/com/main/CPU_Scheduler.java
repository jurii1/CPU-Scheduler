package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Juri
 */
public class CPU_Scheduler {
    public List SJF_Preemptive(List<Process> processes){
        int numberOfProcesses = processes.size();
        int time = 0;
        Process processInExecution = null;
        int timeWhenStarted = 0;
        List<Process> processesToBeVisualized = new ArrayList<>();
        List<Process> executionList = new ArrayList<>();
        List<Process> finishedProcesses = new ArrayList<>();
        
        while(finishedProcesses.size() != numberOfProcesses){
            Collections.sort(processes, Process.processArrivalTime);//sort processes by arrival time
            Iterator <Process> itr = processes.iterator();
            
            while(itr.hasNext()){
                Process process = itr.next();
                if(process.getArrivalTime()<=time){//add process to execution list (ready queue)
                    executionList.add(process);
                    itr.remove();
                }
            }
            
            //execute process
            if(!executionList.isEmpty()){//if there are ready processes start executing
                Collections.sort(executionList, Process.processBurstTime);//sort by priority (SJF)/ less BT left
                
                if(processInExecution == null){//first process to be executed
                    processInExecution = executionList.get(0);
                    processInExecution.setBurstTime(processInExecution.getBurstTime()-1);
                    timeWhenStarted = time;
                }else if(processInExecution==executionList.get(0)){//if the current process in execution is still the highest prority processInExecution.equals(executionList.get(0))
                    processInExecution.setBurstTime(processInExecution.getBurstTime()-1);
                }else{//new higher priority process
                    if(processInExecution.getBurstTime() !=0){
                        System.out.println("("+timeWhenStarted+")["+ processInExecution.getName() +"]"+"("+ time+")");
                        processesToBeVisualized.add(new Process(timeWhenStarted, processInExecution.getName(), time));
                    }
                    processInExecution = executionList.get(0);
                    processInExecution.setBurstTime(processInExecution.getBurstTime()-1);
                    timeWhenStarted = time;
                }
               
                if(processInExecution.getBurstTime()==0){//process has sucessfully been executed
                    finishedProcesses.add(processInExecution);
                    executionList.remove(processInExecution);
                    //set TT and WT
                    processInExecution.setTurnaroundTime((time+1)-processInExecution.getTurnaroundTime());
                    processInExecution.setWaitingTime(processInExecution.getTurnaroundTime()-processInExecution.getWaitingTime());
                    System.out.println("("+timeWhenStarted+")["+ processInExecution.getName() +"]"+"("+ (time+1)+")");
                    processesToBeVisualized.add(new Process(timeWhenStarted, processInExecution.getName(), (time+1)));
                }
            }
            time++;
        }
        printProcesses(finishedProcesses);
        calculateAverageWTandTT(finishedProcesses);
        return processesToBeVisualized;
    }
    
    public List SJF_IO(List<Process> processes){
        int numberOfProcesses = processes.size();
        int time = 0;
        int timeWhenFree = 0; 
        int a;
        List<Process> processesToBeVisualized = new ArrayList<>();
        List<Process> executionList = new ArrayList<>();
        List<Process> finishedProcesses = new ArrayList<>();
        
        while(finishedProcesses.size() != numberOfProcesses){
            Collections.sort(processes, Process.processArrivalTime);//sort processes by arrival time
            Iterator <Process> itr = processes.iterator();
            
            while(itr.hasNext()){
                Process process = itr.next();
                if(process.getArrivalTime()<=time){//add process to execution list (ready queue)
                    executionList.add(process);
                    itr.remove();
                }
            }
            
            //execute process
            if(!executionList.isEmpty() & time >= timeWhenFree){//if there are ready processes & the CPU is free, start executing
                Collections.sort(executionList, Process.processSJF_IO);//sort by priority (SJF)
                if(executionList.get(0).getCpuBurst() <= executionList.get(0).getBurstTime()){//set var to which value is smaller BT or CPU Burst to determine when the CPU will be available
                    a = executionList.get(0).getCpuBurst();
                }else
                    a = executionList.get(0).getBurstTime();
                
                timeWhenFree = time + a;//set the time when the CPU will be free
                
                if(executionList.get(0).isCpuBound()){//if the process is CPU bound
                    finishedProcesses.add(executionList.get(0));
                    //set TT and WT, then add to finished list and remove from execution list
                    executionList.get(0).setTurnaroundTime(timeWhenFree-executionList.get(0).getTurnaroundTime());
                    executionList.get(0).setWaitingTime(executionList.get(0).getTurnaroundTime()-executionList.get(0).getWaitingTime());
                }else{
                    executionList.get(0).setBurstTime(executionList.get(0).getBurstTime() - executionList.get(0).getCpuBurst());//subtract BT by CPU burst to know how much we have left
                    if(executionList.get(0).getBurstTime()<=0){//the process has finished
                        //set TT and WT, then add to finished list and remove from execution list
                        executionList.get(0).setTurnaroundTime(timeWhenFree-executionList.get(0).getTurnaroundTime());
                        executionList.get(0).setWaitingTime(executionList.get(0).getTurnaroundTime()-executionList.get(0).getWaitingTime());
                        finishedProcesses.add(executionList.get(0));
                    }else{//the process has not finished
                        executionList.get(0).setArrivalTime(timeWhenFree + executionList.get(0).getIoBurst());//set AT to when the process is going to be ready (Time when CPU will be available + I/O Burst)
                        processes.add(executionList.get(0));//add to waiting queue
                    }    
                }
                System.out.println("("+time+")["+ executionList.get(0).getName() +"]"+"("+ timeWhenFree+")");
                processesToBeVisualized.add(new Process(time, executionList.get(0).getName(), timeWhenFree));
                executionList.remove(0);//remove from execution queue
            }
            time++;
        }//end of while loop
        printProcesses(finishedProcesses);
        calculateAverageWTandTT(finishedProcesses);
        return processesToBeVisualized;
    }
    
    public static void calculateAverageWTandTT(List<Process> finishedProcesses){
        int numberOfProcesses = finishedProcesses.size();
        int sumOfWT = 0;
        int sumOfTT = 0;
        double avWT, avTT;
        
        for(Process p: finishedProcesses){
            sumOfWT = sumOfWT + p.getWaitingTime();
            sumOfTT = sumOfTT + p.getTurnaroundTime();
        }
        
        avWT = (double)sumOfWT/numberOfProcesses;
        avTT = (double)sumOfTT/numberOfProcesses;

        System.out.println("Average Waiting Time: " + avWT +"ms.");
        System.out.println("Average Turnaround Time: "+ avTT + "ms.");
    }
    
    public static void printProcesses(List<Process> finishedProcesses){
        for(Process p: finishedProcesses){
            System.out.println(p);
        }
    }
}

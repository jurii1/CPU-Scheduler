# CPU-Scheduler
Simulation and visualization of “SJF I/O bursts” &amp; “SJF Preemptive” algorithms.

For simulating the algorithms the data structure used is ArrayList of “Process” objects. The method takes a List <Process> as parameter and at the ends returns a List <Process> containing the necessary data for visualization.

Both methods use three Lists:

processes => representing the list of all the processes

executionList => representing the ready queue

finishedProcesses => the list of completely executed processes

### SJF I/O method:
Sort processes by arrival time.

If time ≤ arrival time add to executionList, remove from processes list.

Sort executionList by shortest remaining time (burst time or CPU burst, depending on who is the shortest).

Execute the first process on the list by decreasing the Burst Time by the amount of CPU Burst.

If Burst Time > 0, increase the Arrival Time by I/O Burst and add it to processes list and remove from executionList.

If Burst Time ≤ 0, the process is completely executed. Add it to finishedProcesses list and remove from executionList.

Repeat all the steps until all the processes are executed (total number of processes equal to finishedProcesses.size()).
### SJF Preemptive:
Sort processes by arrival time.

If time ≤ arrival time add to executionList, remove from processes list.

Sort executionList by shortest remaining time (burst time).

Execute the first process on the list by decreasing the Burst Time by 1.

If Burst Time = 0, the process is completely executed. Add it to finishedProcesses list and remove from executionList.

Print and save the data when the process is either finished or interrupted.

After incrementing the time repeat all the steps until all the processes are executed (total number of processes equal to finishedProcesses.size()).

### Solving, simulating, and visualizing the exercise on request one by using the coded SJF I/O algorithm:

Output of the CPU Scheduler program:

![image](https://github.com/jurii1/CPU-Scheduler/assets/48659107/657d0b16-fa09-4822-86a6-fb58ec5e6f4d)

Visualization of the CPU process scheduling:

![image](https://github.com/jurii1/CPU-Scheduler/assets/48659107/1756feab-1070-4748-a093-64351c9bbc67)

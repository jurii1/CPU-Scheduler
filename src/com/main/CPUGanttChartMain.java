package com.main;

import java.util.ArrayList;
import com.main.GanttChart.ExtraData;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Juri
 */
public class CPUGanttChartMain extends Application {

    @Override public void start(Stage stage) {

        stage.setTitle("CPU Scheduling");
        
        CPU_Scheduler c = new CPU_Scheduler();
        List<Process> arraylist = new ArrayList<Process>();
        List<Process> finishedProcesses = new ArrayList<>();
        
        
        arraylist.add(new Process("P1", 0, 10));
        arraylist.add(new Process("P2", 1, 1));
        arraylist.add(new Process("P3", 3, 4));
        arraylist.add(new Process("P4", 5, 2));
        arraylist.add(new Process("P5", 11, 3));
        
        /*
        arraylist.add(new Process("P1", 0, 10));
        arraylist.add(new Process("P2", 3, 3));
        arraylist.add(new Process("P3", 5, 6));
        arraylist.add(new Process("P4", 6, 3));
        arraylist.add(new Process("P5", 12, 5));
        */
        
        /*
        arraylist.add(new Process("P1", 0, 7, 3, 5));
        arraylist.add(new Process("P2", 2, 9, 5, 2));
        arraylist.add(new Process("P3", 4, 6, true));
        arraylist.add(new Process("P4", 6, 8, 4, 1));
        */
        
        /*arraylist.add(new Process("P1", 0, 3, 4, 3));
        arraylist.add(new Process("P2", 2, 4, 3, 2));
        arraylist.add(new Process("P3", 4, 6, true));
        arraylist.add(new Process("P4", 5, 12, 5, 5));
        arraylist.add(new Process("P5", 7, 8, 3, 1));*/
        
        //select algorithm
        finishedProcesses = c.SJF_Preemptive(arraylist);
        //finishedProcesses = c.SJF_IO(arraylist);
        

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(finishedProcesses.get((finishedProcesses.size()-1)).getEndTime());
        xAxis.setTickUnit(5);
        xAxis.setMinorTickCount(5);
        xAxis.setMinorTickVisible(true);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

        chart.setTitle("Process Scheduling");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);
        String process;

        //first number is the start; second is the duration
        XYChart.Series series1 = new XYChart.Series();
        //add processes name, start, end times
        for(Process p: finishedProcesses){
            process = p.getName();
            series1.getData().add(new XYChart.Data(p.getStartTime(), process, new ExtraData((p.getEndTime() - p.getStartTime()), "status-green")));
        }
        
        chart.getData().addAll(series1);
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        Scene scene  = new Scene(chart,620,350);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.example.demo.DesginPattern;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.demo.Classes.Mashine;
public class Concurrency {
    final static int maxNOfMashines = 5;

    int noOfConcurrentMashines = 0;
    ExecutorService executor = Executors.newFixedThreadPool(maxNOfMashines);

    public void addMashine(Mashine mashine) {
        try {
            if (noOfConcurrentMashines < maxNOfMashines) {
                executor.wait(mashine.getProcessingTime()*1000);
                noOfConcurrentMashines++;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while adding a new mashine");
        }
    }

    public void removeMashine(Mashine mashine) {
        if (noOfConcurrentMashines > 0) {
            executor.shutdown();
            try {
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            noOfConcurrentMashines--;
        }    
    }
    
}

package com.example.demo.DesginPattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.demo.Classes.Machine;
import com.example.demo.WebSocketSender;

public class Concurrency {

    final static int maxNOfMashines = 5;
    private WebSocketSender webSocketSender;

    public Concurrency(WebSocketSender webSocketSender) {
        this.webSocketSender = webSocketSender;
    }

    private int noOfConcurrentMashines = 0;
    private final ExecutorService executor = Executors.newFixedThreadPool(maxNOfMashines);

    public void addMashines(Machine mashine) {
        System.out.println("enter add mashine in concurrecy" + mashine.getProcessingTime());
        if (noOfConcurrentMashines < maxNOfMashines) {
            noOfConcurrentMashines++;
            executor.submit(() -> {
                System.out.println("enter add mashine in concurrecy");

                try {
                    System.out.println("Machine " + mashine.getId() + " has started processing.");
                    Thread.sleep(mashine.getProcessingTime() * 100);
                    System.out.println("Machine " + mashine.getId() + " has finished processing.");
                    mashine.setBusy(false);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Machine " + mashine.getId() + " was interrupted.");
                } finally {
                    removeMashine();
                    mashine.notifyAllQueue();
                }
            });
        } else {
            System.out.println("Maximum number of concurrent machines reached.");
        }
    }

    private synchronized void removeMashine() {
        if (noOfConcurrentMashines > 0) {
            noOfConcurrentMashines--;
        }
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}

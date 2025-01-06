package com.example.demo.DesginPattern;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.demo.Classes.Machine;
import com.example.demo.WebSocketSender;

public class Concurrency {

    final static int maxNOfMashines = 10;
    private WebSocketSender webSocketSender;

    public Concurrency(WebSocketSender webSocketSender) {
        this.webSocketSender = webSocketSender;
    }

    private int noOfConcurrentMashines = 0;
    private final ExecutorService executor = Executors.newFixedThreadPool(maxNOfMashines);

    public void addMashines(Machine mashine) {
        System.out.println("enter add mashine in concurrecy" + mashine.getId());
        if (noOfConcurrentMashines < maxNOfMashines) {
            noOfConcurrentMashines++;
            executor.submit(() -> {
                System.out.println("enter add mashine in concurrecy");

                try {
                    Map<String, String> data = Map.of("type", "machine", "id", String.valueOf(mashine.getId()),
                            "isBusy", String.valueOf(mashine.isBusy()), "color", mashine.getProductColor());
                    webSocketSender.sendMessage("/topic/messages", data);
                    System.out.println("Machine " + mashine.getId() + " has started processing.");
                    Thread.sleep(mashine.getProcessingTime() * 300);
                    System.out.println("Machine " + mashine.getId() + " has finished processing.");
                    mashine.setBusy(false);
                    data = Map.of("type", "machine", "id", String.valueOf(mashine.getId()), "isBusy",
                            String.valueOf(mashine.isBusy()));
                    webSocketSender.sendMessage("/topic/messages", data);

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
            if (noOfConcurrentMashines == 0) {
                System.out.println("All machines have finished processing.");
                webSocketSender.sendMessage("/topic/messages",
                        Map.of("type", "end", "message", "All machines have finished processing."));
            }
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

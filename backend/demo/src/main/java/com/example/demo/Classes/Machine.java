package com.example.demo.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.demo.DesginPattern.Concurrency;
import com.example.demo.DesginPattern.Observer;
import com.example.demo.WebSocketSender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Machine implements Observer {

    private WebSocketSender webSocketSender;
    private Concurrency concurrency;

    private int id;
    private boolean isBusy;// true if the mashine is working on a product
    private int processingTime;// time needed to process a product
    private List<Queue> inQueues = new ArrayList<>();
    private List<Queue> outQueues = new ArrayList<>();
    private boolean pendingProduct;
    private String productColor;// true if the mashine has a product to process

    public Machine(WebSocketSender webSocketSender) {
        this.webSocketSender = webSocketSender;
        concurrency = new Concurrency(webSocketSender);
        Random random = new Random();

        this.processingTime = 5 + random.nextInt(6); // Generates a random value between 5 and 10 (inclusive)
    }

    @Override
    public void notifyAllQueue() {
        Map<String, String> message = Map.of("message", "Machine " + id + " is finish the product and is available");
        System.out.println("message: " + message);

        webSocketSender.sendMessage("/topic/messages", message);

        int minProduct = 0;
        int minIndex = 0;
        for (int i = 0; i < outQueues.size(); i++) {
            if (outQueues.get(i).getPendingProduct() < minProduct) {
                minProduct = outQueues.get(i).getPendingProduct();
                minIndex = i;
            }
        }

        outQueues.get(minIndex).incrmentProducts(productColor);
        for (Queue q : outQueues) {
            q.processProduct();
        }

        for (Queue q : inQueues) {

            q.processProduct();
        }

    }

    public void addInQueue(Queue q) {
        inQueues.add(q);
    }

    public void removeInQueue(Queue q) {
        inQueues.remove(q);
    }

    public void addOutQueue(Queue q) {
        outQueues.add(q);
    }

    public void removeOutQueue(Queue q) {
        outQueues.remove(q);
    }

    public void processProduct() {
        if (pendingProduct) {
            isBusy = true;
            pendingProduct = false;
            try {
                concurrency.addMashines(this);

            } catch (Exception e) {
                System.out.println("Machine " + id + " was interrupted.");
            }
        }
    }

    public void exitProcess() {
        concurrency.shutdown();

    }

}

package com.example.demo.Classes;

import java.util.List;
import java.util.Random;

import com.example.demo.DesginPattern.Observer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Machine implements Observer {

    private int id;
    private boolean isBusy;// true if the mashine is working on a product
    private int processingTime;// time needed to process a product
    private List<Queue> inQueues;
    private List<Queue> outQueues;
    private boolean pendingProduct;// true if the mashine has a product to process

    public Machine() {
        Random random = new Random();

        this.processingTime = 5 + random.nextInt(6); // Generates a random value between 5 and 10 (inclusive)
    }

    @Override
    public void notifyall() {
        for (Queue q : inQueues) {

            q.processProduct();
        }
        for (Queue q : outQueues) {
            q.setPendingProduct(q.getPendingProduct() + 1);
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
                Thread.sleep(processingTime * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isBusy = false;
            notifyall();
        }
    }

}

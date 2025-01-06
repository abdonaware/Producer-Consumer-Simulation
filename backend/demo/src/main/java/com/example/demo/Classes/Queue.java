package com.example.demo.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

import com.example.demo.DesginPattern.Observer;
import com.example.demo.WebSocketSender;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Queue implements Observer {

    private WebSocketSender webSocketSender;
    private List<Machine> inMashines = new ArrayList<>();
    private List<Machine> outMashines = new ArrayList<>();
    private java.util.Queue<String> products = new LinkedList<>();
    private Queue nextQueue;
    private int pendingProduct;
    private int id;
    private boolean startQueue;
    private boolean endQueue;

    public Queue(WebSocketSender webSocketSender) {
        this.webSocketSender = webSocketSender;
        pendingProduct = 0;
    }

    public synchronized void incrmentProducts(String product) {
        pendingProduct++;
        products.add(product);
        Map<String, String> data = Map.of("type", "queue", "id", String.valueOf(id), "pendingProduct",
                String.valueOf(pendingProduct));
        webSocketSender.sendMessage("/topic/messages", data);
    }

    public synchronized void decrmentProducts() {
        pendingProduct--;
        Map<String, String> data = Map.of("type", "queue", "id", String.valueOf(id), "pendingProduct",
                String.valueOf(pendingProduct));
        webSocketSender.sendMessage("/topic/messages", data);
    }

    @Override
    public void notifyAllQueue() {
        for (Machine m : outMashines) {
            m.setPendingProduct(true);
        }
    }

    public void addMashine(Machine m) {
        inMashines.add(m);
    }

    public void removeMashine(Machine m) {
        inMashines.remove(m);
    }

    public void addOutMashine(Machine m) {
        outMashines.add(m);
    }

    public void removeOutMashine(Machine m) {
        outMashines.remove(m);
    }

    public synchronized void processProduct() {
        if (pendingProduct > 0) {

            for (Machine m : inMashines) {
                if (m.isBusy() == false) {
                    m.setPendingProduct(true);
                    m.setProductColor(products.poll());
                    decrmentProducts();
                    Map<String, String> message = Map.of("message",
                            "Machine " + m.getId() + " is processing a product");
                    webSocketSender.sendMessage("/topic/messages", message);
                    m.processProduct();
                }
                if (pendingProduct <= 0) {
                    break;
                }
            }
        }
    }

}

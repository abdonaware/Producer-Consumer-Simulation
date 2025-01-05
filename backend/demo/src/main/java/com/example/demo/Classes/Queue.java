package com.example.demo.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private List<Products> products;
    private int pendingProduct;
    private int id;
    private boolean startQueue;
    private boolean endQueue;

    public Queue(WebSocketSender webSocketSender) {
        this.webSocketSender = webSocketSender;
    }

    @Override
    public void notifyAllQueue() {
        for (Machine m : outMashines) {
            m.setPendingProduct(true);
        }
    }

    public void addProduct(Products p) {
        products.add(p);
        pendingProduct++;
    }

    public void removeProduct(Products p) {
        products.remove(p);
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

    public void processProduct() {
        if (pendingProduct > 0) {
            for (Machine m : inMashines) {
                if (m.isBusy()==false) {
                    m.setPendingProduct(true);
                    Map<String, String> message = Map.of("message", "Machine " + m.getId() + " is processing a product");
                    webSocketSender.sendMessage( "/topic/messages",message);
                    pendingProduct--;
                    m.processProduct();
                    break;
                }
            }
        }
    }

}

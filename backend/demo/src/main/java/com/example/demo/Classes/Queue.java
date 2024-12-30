package com.example.demo.Classes;

import java.util.List;

import com.example.demo.DesginPattern.Observer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Queue implements Observer {
    private List<Machine> inMashines;
    private List<Machine> outMashines;
    private List<Products> products;
    private int pendingProduct;
    private int id;
    private boolean startQueue;
    private boolean endQueue;

    public Queue() {
    }

    @Override
    public void notifyall() {
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
                if (!m.isBusy()) {
                    m.setBusy(true);
                    m.setPendingProduct(true);
                    pendingProduct--;
                    m.processProduct();

                    break;
                }
            }
        }
    }

}

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
    private int noOfProducts;

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
        noOfProducts++;
    }

    public void removeProduct(Products p) {
        products.remove(p);
        noOfProducts--;
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
        if (noOfProducts > 0) {
            for (Machine m : inMashines) {
                if (!m.isBusy()) {
                    m.setBusy(true);
                    if (!products.isEmpty()) {
                        m.setPendingProduct(true);
                    }
                    products.remove(0);
                    noOfProducts--;
                    break;
                }
            }
        }
    }

}

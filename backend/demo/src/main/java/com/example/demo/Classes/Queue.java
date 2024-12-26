package com.example.demo.Classes;
import java.util.List;

import com.example.demo.DesginPattern.Observer;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Queue implements Observer {
    private List<Mashine> inMashines;
    private List<Mashine> outMashines;
    private List<Products> products;
    private int noOfProducts;

    public Queue() { }  
    
    @Override
    public void notifyall() {
       for(Mashine m : outMashines){
           m.setPendingProduct(true);
       }
    }
    public void addProduct(Products p){
        products.add(p);
        noOfProducts++;
    }
    public void removeProduct(Products p){
        products.remove(p);
        noOfProducts--;
    }
    public void addMashine(Mashine m){
        inMashines.add(m);
    }
    public void removeMashine(Mashine m){
        inMashines.remove(m);
    }
    public void addOutMashine(Mashine m){
        outMashines.add(m);
    }
    public void removeOutMashine(Mashine m){
        outMashines.remove(m);
    }
    public void processProduct(){
        if(noOfProducts>0){
            for(Mashine m : inMashines){
                if(!m.isBusy()){
                    m.setBusy(true);
                    if(!products.isEmpty()){
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

package com.example.demo.Classes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Products {

    private String name;
    private int id;

    public Products() {
    }

    public Products(String name, int id) {
        this.name = name;
        this.id = id;
    }

}

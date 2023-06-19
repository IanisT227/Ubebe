package com.company.model;

public class Product {
    private int price;
    private int quantity;
    private int name;

    public Product(int price, int quantity, int name) {
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getName() {
        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", quantity=" + quantity +
                ", name=" + name +
                '}';
    }
}

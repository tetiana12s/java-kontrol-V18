package com.exam.order.model;

import java.util.Objects;

public class OrderItem {
    private final String name;
    private final double price;

    public OrderItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem item = (OrderItem) o;
        return Double.compare(item.price, price) == 0 && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name, price); }

    @Override
    public String toString() { return "Item: " + name + " ($" + price + ")"; }
}

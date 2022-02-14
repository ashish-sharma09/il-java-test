package co.uk.henry.model;

import java.util.List;

public class Basket {

    private final List<Item> items;
    private final Double totalCost;

    public Basket(List<Item> items, Double totalCost) {
        this.items = items;
        this.totalCost = totalCost;
    }

    public List<Item> getItems() {
        return items;
    }

    public Double getTotalCost() {
        return totalCost;
    }
}

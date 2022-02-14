package co.uk.henry.model;

import java.util.Collection;

public class Basket {

    private final Collection<Item> items;
    private final Double totalCost;

    public Basket(final Collection<Item> items, final Double totalCost) {
        this.items = items;
        this.totalCost = totalCost;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public Double getTotalCost() {
        return totalCost;
    }
}

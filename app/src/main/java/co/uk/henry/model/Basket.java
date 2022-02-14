package co.uk.henry.model;

import java.time.LocalDate;
import java.util.Collection;

public class Basket {
    private final Collection<Item> items;
    private final double totalCost;
    private final LocalDate localDate;

    public Basket(final Collection<Item> items, final double totalCost) {
        this.items = items;
        this.totalCost = totalCost;
        this.localDate = LocalDate.now();
    }

    public Basket(final Collection<Item> items, final double totalCost, final LocalDate localDate) {
        this.items = items;
        this.totalCost = totalCost;
        this.localDate = localDate;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}

package co.uk.henry.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Collection;

public class Basket {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

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

    public String getDisplayTotalCost() {
        return DECIMAL_FORMAT.format(totalCost);
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}

package co.uk.henry.model;

public class BasketItem {
    private final Item item;
    private final int quantity;

    public BasketItem(final Item item, final int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double price() {
        return item.getPrice() * quantity;
    }
}

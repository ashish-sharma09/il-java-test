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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketItem)) return false;

        BasketItem that = (BasketItem) o;

        if (quantity != that.quantity) return false;
        return item != null ? item.equals(that.item) : that.item == null;
    }

    @Override
    public int hashCode() {
        int result = item != null ? item.hashCode() : 0;
        result = 31 * result + quantity;
        return result;
    }
}

package co.uk.henry.promotion;

public class Quantity {

    private final int minQuantity;
    private final int maxQuantity;

    public Quantity(final int minQuantity, final int maxQuantity) {
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    public Quantity(final int minQuantity) {
        this.minQuantity = minQuantity;
        this.maxQuantity = Integer.MAX_VALUE;
    }

    public boolean appliesTo(int quantity) {
        return quantity >= minQuantity && quantity <= maxQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantity)) return false;

        Quantity quantity = (Quantity) o;

        if (minQuantity != quantity.minQuantity) return false;
        return maxQuantity == quantity.maxQuantity;
    }

    @Override
    public int hashCode() {
        int result = minQuantity;
        result = 31 * result + maxQuantity;
        return result;
    }
}

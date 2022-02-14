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
}

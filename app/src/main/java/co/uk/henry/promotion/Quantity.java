package co.uk.henry.promotion;

public class Quantity {

    private final Integer minQuantity;
    private final Integer maxQuantity;

    public Quantity(final Integer minQuantity, final Integer maxQuantity) {
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    public Quantity(final Integer minQuantity) {
        this.minQuantity = minQuantity;
        this.maxQuantity = Integer.MAX_VALUE;
    }

    public boolean appliesTo(int quantity) {
        return quantity >= minQuantity && quantity <= maxQuantity;
    }
}

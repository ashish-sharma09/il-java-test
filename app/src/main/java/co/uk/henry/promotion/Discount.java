package co.uk.henry.promotion;

public class Discount {
    private final DiscountUnit discountUnit;
    private final double amount;

    public Discount(DiscountUnit discountUnit, double amount) {
        this.discountUnit = discountUnit;
        this.amount = amount;
    }

    public DiscountUnit getDiscountUnit() {
        return discountUnit;
    }

    public double getAmount() {
        return amount;
    }

    public double applyTo(final double price) {
        if (price <= 0) {
            return 0;
        }

        if (discountUnit == DiscountUnit.PERCENT) {
            return price * amount / 100;
        }
        return 0;
    }
}

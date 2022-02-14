package co.uk.henry.promotion;

public class Discount {
    private final DiscountType type;
    private final String code;
    private final DiscountUnit discountUnit;
    private final double amount;

    public Discount(DiscountType type, DiscountUnit discountUnit, double amount) {
        this(type, null, discountUnit, amount);
    }

    public Discount(DiscountType type, String code) {
        this(type, code, null, 0.0);
    }

    private Discount(DiscountType type, String code, DiscountUnit discountUnit, double amount) {
        this.type = type;
        this.code = code;
        this.discountUnit = discountUnit;
        this.amount = amount;
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

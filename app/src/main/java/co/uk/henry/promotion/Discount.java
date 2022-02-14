package co.uk.henry.promotion;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Discount {
    private final DiscountType type;
    private final String code;
    private final DiscountUnit unit;
    private final double value;


    public Discount(DiscountType type, DiscountUnit unit, double value) {
        this(type, null, unit, value);
    }

    public Discount(DiscountType type, String code) {
        this(type, code, null, 0.0);
    }

    @JsonCreator
    public Discount(DiscountType type, String code, DiscountUnit unit, double value) {
        this.type = type;
        this.code = code;
        this.unit = unit;
        this.value = value;
    }

    public double applyTo(final double price) {
        if (price <= 0) {
            return 0;
        }

        if (unit == DiscountUnit.PERCENT) {
            return price * value / 100;
        }
        return 0;
    }
}

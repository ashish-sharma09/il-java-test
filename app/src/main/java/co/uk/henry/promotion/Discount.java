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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount)) return false;

        Discount discount = (Discount) o;

        if (Double.compare(discount.value, value) != 0) return false;
        if (type != discount.type) return false;
        if (code != null ? !code.equals(discount.code) : discount.code != null) return false;
        return unit == discount.unit;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type != null ? type.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;

import java.time.LocalDate;

public class Promotion {

    private final String code;
    private final PromotionType type;
    private final String item;
    private final Quantity quantity;
    private final Discount discount;
    private final ValidityPeriod validityPeriod;

    public Promotion(
            String code, PromotionType type, String item, Quantity quantity,
            Discount discount, ValidityPeriod validityPeriod
    ) {
        this.code = code;
        this.type = type;
        this.item = item;
        this.quantity = quantity;
        this.discount = discount;
        this.validityPeriod = validityPeriod;
    }

    public boolean isApplicableTo(final BasketItem item, final LocalDate basketDate) {
        return
                this.item.equals(item.getItem().getCode())
                        &&
                        quantity.appliesTo(item.getQuantity())
                        &&
                        validityPeriod.isValidFor(basketDate);
    }

    public double appliedDiscountTo(double itemPrice) {
        return discount.applyTo(itemPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Promotion)) return false;

        Promotion promotion = (Promotion) o;

        if (code != null ? !code.equals(promotion.code) : promotion.code != null) return false;
        if (type != promotion.type) return false;
        if (item != null ? !item.equals(promotion.item) : promotion.item != null) return false;
        if (quantity != null ? !quantity.equals(promotion.quantity) : promotion.quantity != null) return false;
        if (discount != null ? !discount.equals(promotion.discount) : promotion.discount != null) return false;
        return validityPeriod != null ? validityPeriod.equals(promotion.validityPeriod) : promotion.validityPeriod == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (validityPeriod != null ? validityPeriod.hashCode() : 0);
        return result;
    }
}

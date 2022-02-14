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
}

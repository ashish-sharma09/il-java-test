package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;

import java.time.LocalDate;

public class Promotion {

    private final String code;
    private final String itemCode;
    private final Quantity quantity;
    private final Discount discount;
    private final ValidityPeriod validityPeriod;

    public Promotion(
            String code, String itemCode, Quantity quantity,
            Discount discount, ValidityPeriod validityPeriod
    ) {
        this.code = code;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.discount = discount;
        this.validityPeriod = validityPeriod;
    }

    public boolean isApplicableTo(final BasketItem item, final LocalDate basketDate) {
        return
                itemCode.equals(item.getItem().getCode())
                        &&
                        quantity.appliesTo(item.getQuantity())
                        &&
                        validityPeriod.isValidFor(basketDate);
    }

    public double appliedDiscountTo(double itemPrice) {
        return discount.applyTo(itemPrice);
    }

    /*
            code        - P1
            type        - main-promo
            item        - 4
            unit        - single
            minQuantity - 1
            maxQuantity - na
            validFrom   - -P1D
            validTo     -  P7D
            discount
                type       - money
                code       - na
                unit       - percent
                value      - 10


            code        - P2
            type        - main-promo
            item        - 1
            unit        - tins
            minQuantity - 2
            maxQuantity - 2
            validFrom   - P3D
            validTo     - P2M
            discount
                type        - sub-promo
                code        - P3
                unit        - na
                value       - na

            code        - P3
            type        - sub-promo
            item        - 2
            unit        - loaf
            minQuantity - 1
            maxQuantity - 1
            validFrom   -
            validTo     -
            discount
                type        - money
                code        - na
                unit        - percentage
                value       - 10
    */

}

package co.uk.henry.promotion;

public class Promotion {

    private final String code;
    private final String itemCode;
    private final Quantity quantity;
    private final Discount discount;

    public Promotion(String code, String itemCode, Quantity quantity, Discount discount) {
        this.code = code;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public String getItemCode() {
        return itemCode;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Discount getDiscount() {
        return discount;
    }

    /*
            code        - P1
            type        - main-promo
            item        - 4
            unit        - single
            minQuantity - 1
            maxQuantity - na
            validFrom   - -1d
            validTo     -  7d
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
            validFrom   - 3d
            validTo     - 1mo+
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

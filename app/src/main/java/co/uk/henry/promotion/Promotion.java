package co.uk.henry.promotion;

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

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }
}

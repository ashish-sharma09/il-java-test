package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;

import java.math.BigDecimal;
import java.util.List;

public class PromotionServiceImpl implements PromotionService {
    @Override
    public BigDecimal getCostWithApplicableDiscountsfor(final List<BasketItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("basket items cannot be null or empty");
        }
        return null;
    }
}

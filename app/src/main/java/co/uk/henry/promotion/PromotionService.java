package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;

import java.math.BigDecimal;
import java.util.List;

public interface PromotionService {
    double getApplicableTotalDiscountFor(List<BasketItem> items);
}
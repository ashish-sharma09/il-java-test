package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;

import java.time.LocalDate;
import java.util.List;

public interface PromotionService {
    double getApplicableTotalDiscountFor(List<BasketItem> items, LocalDate basketDate);
}
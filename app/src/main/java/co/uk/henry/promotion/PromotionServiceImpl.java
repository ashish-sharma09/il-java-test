package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;
import co.uk.henry.model.Item;
import java.util.List;

public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public double getApplicableTotalDiscountFor(final List<BasketItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("basket items cannot be null or empty");
        }

        final List<Promotion> promotions = promotionRepository.getPromotions();

        if (promotions.isEmpty()) {
            return 0;
        }

        final Promotion promotion = promotions.get(0);
        final Item basketItem = items.get(0).getItem();

        return promotion.getDiscount().applyTo(basketItem.getPrice());
    }
}

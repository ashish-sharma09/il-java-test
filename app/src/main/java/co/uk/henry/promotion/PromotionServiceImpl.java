package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;
import java.util.List;
import java.util.stream.Collectors;

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

        // filter promotion applicable to the item
        final List<Promotion> applicablePromotions = promotions.stream()
                .filter(promotion ->
                        items.stream().anyMatch(basketItem ->
                                basketItem.getItem().getCode().equals(promotion.getItemCode())
                                        &&
                                        promotion.getQuantity().appliesTo(basketItem.getQuantity())
                        )
                )
                .collect(Collectors.toList());

        final BasketItem basketItem = items.get(0);

        return applicablePromotions.stream()
                .map(Promotion::getDiscount)
                .mapToDouble(
                        discount -> discount.applyTo(basketItem.price())
                ).sum();
    }
}

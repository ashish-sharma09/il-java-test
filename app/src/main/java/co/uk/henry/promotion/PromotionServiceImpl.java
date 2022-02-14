package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public double getApplicableTotalDiscountFor(final List<BasketItem> items, final LocalDate basketDate) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("basket items cannot be null or empty");
        }

        final List<Promotion> promotions = promotionRepository.getPromotions();

        if (promotions.isEmpty()) {
            return 0;
        }

        final Map<BasketItem, List<Promotion>> itemToPromotions = items.stream()
                .map(item ->
                    Map.entry(item, promotions.stream()
                        .filter(promotion ->
                            promotion.getItemCode().equals(item.getItem().getCode())
                                &&
                                promotion.getQuantity().appliesTo(item.getQuantity())
                                &&
                                promotion.getValidityPeriod().isValidFor(basketDate)
                        ).collect(Collectors.toList())
                    )
                ).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        return itemToPromotions.entrySet().stream()
                .mapToDouble(entry ->
                    entry.getValue().stream()
                        .mapToDouble(promo -> promo.getDiscount().applyTo(entry.getKey().price())).sum()
                ).sum();
    }
}

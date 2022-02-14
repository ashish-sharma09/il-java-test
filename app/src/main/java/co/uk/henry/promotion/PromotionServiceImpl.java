package co.uk.henry.promotion;

import co.uk.henry.model.BasketItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.min;
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
                .map(item -> Map.entry(item, applicablePromotions(item, basketDate, promotions)))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        final List<Promotion> allShortlistedPromos =
                itemToPromotions.values().stream().flatMap(List::stream).collect(Collectors.toList());

        return itemToPromotions.entrySet().stream()
                .filter(entry -> isMainPromotionOrSubPromotionWithMain(entry, allShortlistedPromos))
                .mapToDouble(entry ->
                        entry.getValue().stream()
                                .mapToDouble(promo ->
                                        promo.appliedDiscountTo(
                                                entry.getKey().priceFor(min(entry.getKey().getQuantity(), promo.maxApplicableQuantity()))
                                        )
                                ).sum()
                ).sum();
    }

    private boolean isMainPromotionOrSubPromotionWithMain(
            Map.Entry<BasketItem, List<Promotion>> entry, List<Promotion> allShortlistedPromos
    ) {
        return entry.getValue().stream()
                .anyMatch(promotion ->
                        promotion.isMain()
                                ||
                                (promotion.isSub() && allShortlistedPromos.stream().anyMatch(Promotion::isMain))
                );
    }

    private List<Promotion> applicablePromotions(
            BasketItem item, LocalDate basketDate, List<Promotion> promotions
    ) {
        return promotions.stream()
                .filter(promotion -> promotion.isApplicableTo(item, basketDate))
                .collect(Collectors.toList());
    }
}

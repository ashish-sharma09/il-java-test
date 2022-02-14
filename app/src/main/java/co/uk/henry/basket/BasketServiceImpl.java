package co.uk.henry.basket;

import co.uk.henry.model.Basket;
import co.uk.henry.model.BasketItem;
import co.uk.henry.model.Item;
import co.uk.henry.promotion.PromotionService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasketServiceImpl implements BasketService {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private final List<BasketItem> items;
    private final PromotionService promotionService;

    public BasketServiceImpl(final PromotionService promotionService) {
        this.promotionService = promotionService;
        this.items = new ArrayList<>();
    }

    @Override
    public void add(final Item item, final int quantity) {
        // pre-assertions
        if (item == null) {
            throwIllegalArgument("An item cannot be null");
        }
        if (quantity <= 0) {
            throwIllegalArgument("An item quantity cannot be less than or equal to 0");
        }

        items.add(new BasketItem(item, quantity));
    }

    @Override
    public Basket getBasketFor(final Period day) {

        final double applicableDiscount =
                promotionService.getApplicableTotalDiscountFor(items, LocalDate.now().plusDays(day.getDays()));

        // truncate to two decimal places
        final String basketCost = DECIMAL_FORMAT.format(
                items.stream().mapToDouble(BasketItem::price).sum() - applicableDiscount
        );

        return new Basket(
                items.stream().map(BasketItem::getItem).collect(Collectors.toList()),
                Double.parseDouble(basketCost)
        );
    }

    private void throwIllegalArgument(String An_item_cannot_be_null) {
        throw new IllegalArgumentException(An_item_cannot_be_null);
    }
}

package co.uk.henry.basket;

import co.uk.henry.model.Basket;
import co.uk.henry.model.Item;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasketServiceImpl implements BasketService {

    private final List<BasketItem> items;

    public BasketServiceImpl() {
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
    public Basket getBasketFor(Period day) {
        return new Basket(
                items.stream().map(basketItem -> basketItem.item).collect(Collectors.toList()),
                items.stream().mapToDouble(BasketItem::cost).sum()
        );
    }

    private void throwIllegalArgument(String An_item_cannot_be_null) {
        throw new IllegalArgumentException(An_item_cannot_be_null);
    }

    private static class BasketItem {
        private final Item item;
        private final int quantity;

        private BasketItem(final Item item, final int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        private Double cost() {
            return item.getPrice() * quantity;
        }
    }
}

package co.uk.henry.basket;

import co.uk.henry.model.Basket;
import co.uk.henry.model.Item;

import java.util.ArrayList;
import java.util.List;

public class BasketServiceImpl implements BasketService {

    private final List<Item> items;

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

        items.add(item);
    }

    @Override
    public Basket getBasket() {
        return new Basket(items, items.stream().mapToDouble(Item::getPrice).sum());
    }

    private void throwIllegalArgument(String An_item_cannot_be_null) {
        throw new IllegalArgumentException(An_item_cannot_be_null);
    }
}

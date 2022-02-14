package co.uk.henry.basket;

import co.uk.henry.model.Basket;
import co.uk.henry.model.Item;

public class BasketServiceImpl implements BasketService {

    @Override
    public void add(Item item, int quantity) {
        // pre-assertions
        if (item == null) {
            throwIllegalArgument("An item cannot be null");
        }
        if (quantity <= 0) {
            throwIllegalArgument("An item quantity cannot be less than or equal to 0");
        }
    }

    @Override
    public Basket getBasket() {
        return null;
    }

    private void throwIllegalArgument(String An_item_cannot_be_null) {
        throw new IllegalArgumentException(An_item_cannot_be_null);
    }
}

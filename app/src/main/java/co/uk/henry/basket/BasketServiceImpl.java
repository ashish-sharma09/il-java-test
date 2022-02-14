package co.uk.henry.basket;

import co.uk.henry.model.Basket;
import co.uk.henry.model.Item;

public class BasketServiceImpl implements BasketService {

    @Override
    public void add(Item item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("An item cannot be null");
        }
    }

    @Override
    public Basket getBasket() {
        return null;
    }
}

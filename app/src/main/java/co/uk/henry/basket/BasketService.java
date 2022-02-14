package co.uk.henry.basket;

import co.uk.henry.model.Basket;
import co.uk.henry.model.Item;

public interface BasketService {
    void add(Item item, int quantity);
    Basket getBasket();
}
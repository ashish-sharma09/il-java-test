package co.uk.henry.product;

import co.uk.henry.model.Item;

import java.util.List;

public interface ProductRepository {
    List<Item> getProducts();
}
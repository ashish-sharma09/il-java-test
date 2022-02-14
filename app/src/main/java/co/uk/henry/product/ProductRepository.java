package co.uk.henry.product;

import co.uk.henry.model.Item;

import java.util.List;

interface ProductRepository {
    List<Item> getProducts();
}
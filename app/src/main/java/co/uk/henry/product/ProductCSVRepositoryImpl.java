package co.uk.henry.product;

import co.uk.henry.model.Item;

import java.nio.file.Path;
import java.util.List;

public class ProductCSVRepositoryImpl implements ProductRepository {

    private final Path itemsCSVFilePath;

    public ProductCSVRepositoryImpl(final Path itemsCSVFilePath) {
        this.itemsCSVFilePath = itemsCSVFilePath;

        if (!itemsCSVFilePath.toFile().exists()) {
            throw new IllegalStateException(String.format("Given CSV file with path: %s does not exist.", itemsCSVFilePath));
        }
    }

    @Override
    public List<Item> getProducts() {
        return null;
    }
}

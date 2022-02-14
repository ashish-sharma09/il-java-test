package co.uk.henry.product;

import co.uk.henry.model.Item;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;

public class ProductCSVRepositoryImpl implements ProductRepository {

    private final CSVReader csvReader;

    public ProductCSVRepositoryImpl(final Path itemsCSVFilePath) {
        try {
            this.csvReader = new CSVReader(new FileReader(itemsCSVFilePath.toFile()));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(String.format("Given CSV file with path: %s does not exist.", itemsCSVFilePath));
        }
    }

    @Override
    public List<Item> getProducts() {

        return null;
    }
}

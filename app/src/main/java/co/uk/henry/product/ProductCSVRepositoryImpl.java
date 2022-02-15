package co.uk.henry.product;

import co.uk.henry.model.Item;
import co.uk.henry.model.Unit;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCSVRepositoryImpl implements ProductRepository {

    private final CSVReader csvReader;

    public ProductCSVRepositoryImpl(final Path itemsCSVFilePath) {
        try {
            this.csvReader = new CSVReader(new FileReader(itemsCSVFilePath.toFile()));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new IllegalStateException(
                    String.format("Given CSV file with path: %s does not exist.", itemsCSVFilePath)
            );
        }
    }

    ProductCSVRepositoryImpl(final CSVReader csvReader) {
        this.csvReader  = csvReader;
    }

    @Override
    public List<Item> getProducts() {
        try {
            final List<String[]> itemRows = csvReader.readAll();
            itemRows.remove(0);

            final List<Item> parsedItems = itemRows.stream().map(
                    row -> new Item(row[0], row[1], Unit.valueOf(row[2].toUpperCase()), Double.parseDouble(row[3]))
            ).collect(Collectors.toList());

            return parsedItems;
        } catch (final IOException | CsvException ioException) {
            throw new RuntimeException("Error while reading all items from CSV file", ioException);
        }
    }

}

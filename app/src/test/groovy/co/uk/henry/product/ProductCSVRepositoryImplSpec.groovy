package co.uk.henry.product

import co.uk.henry.model.Item
import co.uk.henry.model.Unit
import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvException
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class ProductCSVRepositoryImplSpec extends Specification {

    private ProductRepository productRepository

    def "error when given csv file does not exist"() {
        when: "a non existing CSV file is provided"
        Path csvFilePath = Path.of("some/path","someCSVFileName")
        productRepository = new ProductCSVRepositoryImpl(csvFilePath)

        then: "error is thrown complaining about non existent file during initialisation"
        def exception = thrown(IllegalStateException)
        exception.message == "Given CSV file with path: some/path/someCSVFileName does not exist."
    }

    def "All items from CSV file can be read and returned"() {
        given: "an existing CSV file is provided"
        def csvFilePath = Paths.get(this.class.classLoader.getResource("items.csv").toURI())
        productRepository = new ProductCSVRepositoryImpl(csvFilePath)

        when: "items are fetched"
        def products = productRepository.getProducts()

        then: "All items are returned"
        products == [
                new Item("1", "product1", Unit.TIN, 0.65),
                new Item("2", "product2", Unit.LOAF, 0.80),
                new Item("3", "product3", Unit.BOTTLE, 1.30),
        ]
    }

    def "exception while reading csv file is wrapped correctly"() {
        given: "a mock csv reader"
        def csvReader = Mock(CSVReader)
        productRepository = new ProductCSVRepositoryImpl(csvReader)

        and: "csvReader thrown IOException while reading the file"
        csvReader.readAll() >> {throw csvReaderException}

        when: "items are fetched"
        productRepository.getProducts()

        then: "Exception is wrapped in a runtime exception"
        def exception = thrown(RuntimeException)
        exception.cause.class.isInstance(csvReaderException)

        where:
        csvReaderException | _
        new IOException()  | _
        new CsvException() | _
    }
}

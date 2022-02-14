package co.uk.henry.product

import spock.lang.Specification

import java.nio.file.Path

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
}

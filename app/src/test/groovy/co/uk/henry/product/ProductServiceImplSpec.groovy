package co.uk.henry.product

import spock.lang.Specification

class ProductServiceImplSpec extends Specification {

    private ProductService productService
    private ProductRepository productRepository

    void setup() {
        productRepository = Mock()
        productService = new ProductServiceImpl(productRepository)
    }

    def "empty list of products is provided when no products exists in the repository"() {
        given: "there are no products in the repository"
        productRepository.getProducts() >> []

        when: "list of all products are requested"
        def items = productService.getItems()

        then: "items list is empty"
        items == []
    }
}

package co.uk.henry.product

import co.uk.henry.model.Item
import co.uk.henry.model.Unit
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

    def "items is provided as returned by repository"() {
        given: "there are products in the repository"
        def item1 = new Item("itemCode1", "itemName1", Unit.SINGLE, 12.0)
        def item2 = new Item("itemCode2", "itemName2", Unit.SINGLE, 2.0)
        productRepository.getProducts() >> [item1, item2]

        when: "list of all items are requested"
        def items = productService.getItems()

        then: "items list contains all items"
        items == [item1, item2]
    }

    def "Exception thrown by ProductRepository is wrapped around Product Service exception"() {
        given: "exception is thrown from product repository"
        productRepository.getProducts() >> { throw new RuntimeException("product repository error") }

        when: "list of all items are requested"
        productService.getItems()

        then: "ProductServiceException wraps the underlying product repository error"
        Exception exception = thrown(ProductServiceException)
        exception.message == "Error from product repository"
        exception.cause instanceof RuntimeException
    }
}

package co.uk.henry.basket

import co.uk.henry.model.Item
import co.uk.henry.model.Unit
import spock.lang.Specification

class BasketServiceImplSpec extends spock.lang.Specification {

    BasketService basketService = new BasketServiceImpl()

    def "A null item cannot be added to the basket"() {
        when: "a null item is added to the basket"
        basketService.add(null, 1)

        then: "An illegal argument exception is reported"
        def exception = thrown(IllegalArgumentException)

        and: "Appropriate error message exists"
        exception.message == "An item cannot be null"
    }

    @Unroll
    def "An item cannot be added with quantity as #quantity"() {
        given: "An item to be added to the basket"
        Item item = new Item("someItemCode", "someItemName", Unit.SINGLE, 1.0)

        when: "item with #quantity"
        basketService.add(item, quantity)

        then: "An illegal argument exception is reported"
        def exception = thrown(IllegalArgumentException)

        and: "An appropriate error message exists"
        exception.message == "An item quantity cannot be less than or equal to 0"

        where:
        quantity | _
        0        | _
        -1       | _
    }
}

package co.uk.henry.basket

import spock.lang.Specification

class BasketServiceImplSpec extends spock.lang.Specification {

    BasketService basketService = new BasketServiceImpl()

    def "A null item cannot be added to the basket"() {
        when: "a null item is added to the basket"
        basketService.add(null, 1)

        then: "An illegal argument exception is reported"
        thrown(IllegalArgumentException)
    }
}

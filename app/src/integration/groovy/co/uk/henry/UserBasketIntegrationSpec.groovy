package co.uk.henry

import co.uk.henry.basket.BasketService
import co.uk.henry.product.ProductService
import spock.lang.Specification

import java.time.Period

class UserBasketIntegrationSpec extends Specification {

    private BasketService basketService
    private ProductService productService

    private Application application

    void setup() {
        application = new Application()
        basketService = application.getBasketService()
        productService = application.getProductService()
    }

    def "An item can be added to an user's basket and it's price retrieved"() {
        given: "All available products"
        def items = productService.getItems()

        and: "An item has been chosen to be added to the basket"
        def item = items.get(0)

        when: "the chosen item has been added to the basket"
        basketService.add(item, 1)

        then: "basket reflects the added item"
        def basket = basketService.getBasketFor(Period.ofDays(0))
        basket.items == [item]

        and: "the total cost"
        basket.totalCost == item.price
    }

    def "Multiple items can be added to the user's basket and their total price retrieved"() {
        given: "All available products"
        def items = productService.getItems()

        and: "two items to be added"
        def item1 = items.get(0)
        def item2 = items.get(1)

        when: "the chosen item has been added to the basket"
        basketService.add(item1, 1)
        basketService.add(item2, 1)

        then: "basket reflects the added item"
        def basket = basketService.getBasketFor(Period.ofDays(0))
        basket.items == [item1, item2]

        and: "the total cost"
        basket.totalCost == item1.price + item2.price
    }

    def "Multiple items can be added with multiple quantities to the user's basket and their total price retrieved"() {
        given: "All available products"
        def items = productService.getItems()

        and: "two items to be added"
        def item1 = items.get(0)
        def item2 = items.get(1)

        when: "the chosen item has been added to the basket"
        basketService.add(item1, 2)
        basketService.add(item2, 3)

        then: "basket reflects the added item"
        def basket = basketService.getBasketFor(Period.ofDays(0))
        basket.items == [item1, item2]

        and: "the total cost"
        basket.totalCost ==( 2 * item1.price + 3 * item2.price)
    }

    def "Apples have 10% discount applied when added to the basket for the applicable date"() {
        given: "All available products"
        def items = productService.getItems()

        and: "apples to be added"
        def apples = items.find {it.name == "apples"}

        when: "5 apples are added to the basket"
        basketService.add(apples, 5)

        then: "basket reflects the added item"
        def basket = basketService.getBasketFor(Period.ofDays(0))
        basket.items == [apples]

        and: "the total cost"
        basket.totalCost == 0.45
    }
}

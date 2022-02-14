package co.uk.henry

import co.uk.henry.basket.BasketService
import co.uk.henry.product.ProductService
import spock.lang.Specification

import java.text.DecimalFormat
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

        and: "basket retrieved today"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "basket reflects the added item"
        basket.items == [item]

        and: "the total cost"
        basket.totalCost == truncatedToTwoDecimalPlaces(item.price)
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

        and: "basket retrieved today"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "basket reflects the added item"
        basket.items == [item1, item2]

        and: "the total cost"
        basket.totalCost == truncatedToTwoDecimalPlaces(item1.price + item2.price)
    }

    def "Multiple items can be added with multiple quantities to the user's basket and their total price retrieved"() {
        given: "All available products"
        def items = productService.getItems()

        and: "two items to be added"
        def item1 = items.get(0)
        def item2 = items.get(2)

        when: "the chosen item has been added to the basket"
        basketService.add(item1, 2)
        basketService.add(item2, 3)

        and: "basket retrieved today"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "basket reflects the added item"
        basket.items == [item1, item2]

        and: "the total cost"
        basket.totalCost == truncatedToTwoDecimalPlaces(2 * item1.price + 3 * item2.price)
    }

    def "Apples have 10% discount applied when added to the basket for the applicable date"() {
        given: "All available products"
        def items = productService.getItems()

        and: "apples to be added"
        def apples = items.find {it.name == "apples"}

        when: "5 apples are added to the basket"
        basketService.add(apples, 5)

        and:"basket fetched in 4 days time"
        def basket = basketService.getBasketFor(Period.ofDays(4))

        then: "basket reflects the added item"
        basket.items == [apples]

        and: "the total cost"
        basket.totalCost == 0.45D
    }

    def "loaf of bread is half price when two tins of soup are bought for the applicable date else no discount given"() {
        given: "All available products"
        def items = productService.getItems()

        and: "soup tin and bread to be added"
        def soup = items.find {it.name == "soup"}
        def bread = items.find {it.name == "bread"}

        when: "2 tins of soup are added"
        basketService.add(soup, 2)

        and: "one loaf of bread is added"
        basketService.add(bread, 1)

        and: "basket fetched for tomorrow"
        def basket = basketService.getBasketFor(Period.ofDays(days))

        then: "basket reflects the added item"
        basket.items == [soup, bread]

        and: "the total cost with 50% discount on loaf of bread"
        basket.totalCost == expectedPrice

        where:
        days | expectedPrice
        1    | 1.70D
        45   | 2.10D
    }

    def "1 loaf of bread is half price when 3 tins of soup and 2 loaves are bought for the applicable date"() {
        given: "All available products"
        def items = productService.getItems()

        and: "soup tin and bread to be added"
        def soup = items.find {it.name == "soup"}
        def bread = items.find {it.name == "bread"}

        when: "2 tins of soup are added"
        basketService.add(soup, 3)

        and: "one loaf of bread is added"
        basketService.add(bread, 2)

        and: "basket fetched for tomorrow"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "basket reflects the added item"
        basket.items == [soup, bread]

        and: "the total cost with 50% discount on loaf of bread"
        basket.totalCost == 3.15D
    }

    def "6 apples and a bottle of milk for the applicable date"() {
        given: "All available products"
        def items = productService.getItems()

        and: "apples and milk to be added"
        def apples = items.find {it.name == "apples"}
        def milk = items.find {it.name == "milk"}

        when: "6 apples are added"
        basketService.add(apples, 6)

        and: "1 milk is added"
        basketService.add(milk, 1)

        and: "basket fetched for today"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "basket reflects the added item"
        basket.items == [apples, milk]

        and: "the total cost with 50% discount on loaf of bread"
        basket.totalCost == 1.90D
    }

    def "6 apples and a bottle of milk bought in five days time"() {
        given: "All available products"
        def items = productService.getItems()

        and: "apples and milk to be added"
        def apples = items.find {it.name == "apples"}
        def milk = items.find {it.name == "milk"}

        when: "6 apples are added"
        basketService.add(apples, 6)

        and: "1 milk is added"
        basketService.add(milk, 1)

        and: "basket fetched in five days time"
        def basket = basketService.getBasketFor(Period.ofDays(5))

        then: "basket reflects the added item"
        basket.items == [apples, milk]

        and: "the total cost with 50% discount on loaf of bread"
        basket.totalCost == 1.84D
    }

    def "3 apples, 2 tins of soup and a loaf of bread bought in five days time"() {
        given: "All available products"
        def items = productService.getItems()

        and: "apples and milk to be added"
        def apples = items.find {it.name == "apples"}
        def soup = items.find {it.name == "soup"}
        def bread = items.find {it.name == "bread"}

        when: "3 apples are added"
        basketService.add(apples, 3)

        and: "2 tins of soup is added"
        basketService.add(soup, 2)

        and: "a loaf of bread"
        basketService.add(bread, 1)

        and: "basket fetched in five days time"
        def basket = basketService.getBasketFor(Period.ofDays(5))

        then: "basket reflects the added item"
        basket.items == [apples, soup, bread]

        and: "the total cost with 50% discount on loaf of bread"
        basket.totalCost == 1.97D
    }

    private double truncatedToTwoDecimalPlaces(double price) {
        Double.parseDouble(new DecimalFormat("#.##").format(price))
    }
}

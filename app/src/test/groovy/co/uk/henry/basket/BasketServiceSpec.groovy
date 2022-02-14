package co.uk.henry.basket

import co.uk.henry.model.BasketItem
import co.uk.henry.model.Item
import co.uk.henry.model.Unit
import co.uk.henry.promotion.PromotionService
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.Period

class BasketServiceSpec extends Specification {

    private BasketService basketService
    private PromotionService promotionService

    void setup() {
        promotionService = Mock(PromotionService)
        basketService = new BasketServiceImpl(promotionService)

    }

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

    def "An empty basket can be retrieved"() {
        given: "no items are added to the basket"

        when: "basket is retrieved"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "basket is empty"
        basket.items == []
    }

    def "An item can be added to the basket and retrieved successfully with its price"() {
        given: "an item exists"
        Item item = new Item("itemCode", "itemName", Unit.SINGLE, 10.0)

        when: "it is added to the basket"
        basketService.add(item, 1)

        and: "basket is retrieved"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "item exists in the basket"
        basket.items == [item]

        and: "price for quantity 1 is as expected"
        basket.totalCost == 10.0
    }

    def 'Multiple items of quantity 1 each can be added to the basket and retrieved successfully with its total cost'() {
        given: "multiple items exists"
        Item item1 = new Item("itemCode1", "itemName1", Unit.SINGLE, 10.0)
        Item item2 = new Item("itemCode2", "itemName2", Unit.SINGLE, 5.50)

        when: "it is added to the basket"
        basketService.add(item1, 1)
        basketService.add(item2, 1)

        and: "basket is retrieved"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "item exists in the basket"
        basket.items == [item1, item2]

        and: "price for given quantities is as expected"
        basket.totalCost == 15.50
    }

    def "An item with multiple quantity can be added and its basket cost retrieved successfully"() {
        given: "an item exists"
        Item item = new Item("itemCode", "itemName", Unit.SINGLE, 10.0)

        when: "it is added to the basket"
        basketService.add(item, 5)

        and: "basket is retrieved"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "item exists in the basket"
        basket.items == [item]

        and: "price for quantity 1 is as expected"
        basket.totalCost == 50.0
    }

    def "Promotion is applied to the basket when discount is returned for the items"() {
        given: "an item exists"
        Item item = new Item("itemCode", "itemName", Unit.SINGLE, 10.0)

        and: "promotions available"
        promotionService.getApplicableTotalDiscountFor([new BasketItem(item, 2)], LocalDate.now()) >> 1.0

        when: "it is added to the basket"
        basketService.add(item, 2)

        and: "basket is retrieved"
        def basket = basketService.getBasketFor(Period.ofDays(0))

        then: "item exists in the basket"
        basket.items == [item]

        and: "price for quantity with discount deducted"
        basket.totalCost == 19.0
    }
}

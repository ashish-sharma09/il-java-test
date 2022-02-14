package co.uk.henry

import co.uk.henry.basket.BasketService
import co.uk.henry.price.ProductService
import co.uk.henry.promotion.PromotionService
import spock.lang.Specification

class UserBasketIntegrationSpec extends Specification {

    private final BasketService basketService
    private final ProductService productService
    private final PromotionService promotionService

    def "An item can be added to an user's basket and it's price retrieved"() {
        given: "All available products"
        def items = productService.getItems()

        and: "An item has been chosen to be added to the basket"
        def item = items.get(0)

        when: "the chosen item has been added to the basket"
        basketService.add(item, 1)

        then: "basket reflects the added item"
        def basket = basketService.getBasket()
        basket.items == [item]

        and: "the total cost"
        basket.totalCost == item.price
    }
}

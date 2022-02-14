package co.uk.henry.promotion

import co.uk.henry.model.BasketItem
import co.uk.henry.model.Item
import co.uk.henry.model.Unit
import spock.lang.Specification
import spock.lang.Unroll

class PromotionServiceImplSpec extends Specification {

    private def promotionService;
    private def promotionRepository

    void setup() {
        promotionRepository = Mock(PromotionRepository)
        promotionService = new PromotionServiceImpl(promotionRepository)
    }

    @Unroll
    def "throw error when given basket items is #items"() {
        when: "basket items is null"
        promotionService.getApplicableTotalDiscountFor(items)

        then:
        thrown(IllegalArgumentException)

        where:
        items | _
        null  | _
        []    | _
    }

    def "no discounts are applied when no promotions are available"() {
        given: "items in the basket"
        List<BasketItem> basketItems = [
                new BasketItem(new Item("1","someName", Unit.SINGLE, 1.25), 1)
        ]

        and: "no promotions in the repository"
        promotionRepository.getPromotions() >> []

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "cost is unchanged"
        applicableDiscounts == 0.0
    }

    def "discount value is returned when an item in the basket has applicable promotion with 10 percent discount"() {
        given: "items in the basket"
        List<BasketItem> basketItems = [
                new BasketItem(new Item("1","someName", Unit.SINGLE, 4.00), 1)
        ]

        and: "an applicable promotion in the repository"
        promotionRepository.getPromotions() >> [
                new Promotion("P1", "1", new Quantity(1), new Discount(DiscountUnit.PERCENT, 10))
        ]

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "applied discount is returned"
        applicableDiscounts == 0.40
    }
}

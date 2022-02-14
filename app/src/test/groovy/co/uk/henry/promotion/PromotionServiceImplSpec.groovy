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
        given: "item in the basket"
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

    def "discount value is returned when items with more than 1 quantity in the basket has applicable promotion with 15 percent discount"() {
        given: "item in the basket with multiple quantity"
        List<BasketItem> basketItems = [
                new BasketItem(new Item("1","someName", Unit.SINGLE, 4.00), 5),
        ]

        and: "an applicable promotion in the repository"
        promotionRepository.getPromotions() >> [
                new Promotion("P1", "1", new Quantity(1), new Discount(DiscountUnit.PERCENT, 10))
        ]

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "applied discount is returned"
        applicableDiscounts == 2.0
    }

    def "correct promotion is applied when multiple promotions are available"() {
        given: "items in the basket"
        List<BasketItem> basketItems = [
                new BasketItem(new Item("1","someName", Unit.SINGLE, 4.00), 1),
        ]

        and: "an applicable promotion with other promotions in the repository"
        promotionRepository.getPromotions() >> [
                new Promotion("P1", "2", new Quantity(1), new Discount(DiscountUnit.PERCENT, 10)),
                new Promotion("P2", "1", new Quantity(1), new Discount(DiscountUnit.PERCENT, 5))
        ]

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "applied discount is returned"
        applicableDiscounts == 0.2
    }

    def "promotion is not applied when promotion quantity condition does not with basket item's quantity"() {
        given: "items in the basket with quantity 1"
        List<BasketItem> basketItems = [
                new BasketItem(new Item("1","someName", Unit.SINGLE, 4.00), 1),
        ]

        and: "an applicable promotion in the repository with min quantity 2"
        promotionRepository.getPromotions() >> [
                new Promotion("P1", "1", new Quantity(2), new Discount(DiscountUnit.PERCENT, 5))
        ]

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "applied discount is returned"
        applicableDiscounts == 0.0
    }
}

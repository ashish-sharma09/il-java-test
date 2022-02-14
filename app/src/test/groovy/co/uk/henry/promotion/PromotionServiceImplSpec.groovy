package co.uk.henry.promotion

import co.uk.henry.model.BasketItem
import co.uk.henry.model.Item
import co.uk.henry.model.Unit
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Period

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
                new Promotion("P1", "1", new Quantity(1), new Discount(DiscountUnit.PERCENT, 10), new co.uk.henry.promotion.ValidityPeriod(java.time.Period.parse("P0D"), java.time.Period.parse("P2D")))
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
                new Promotion("P1", "1", new Quantity(1), new Discount(DiscountUnit.PERCENT, 10), new co.uk.henry.promotion.ValidityPeriod(java.time.Period.parse("P0D"), java.time.Period.parse("P2D")))
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
                new Promotion("P1", "2", new Quantity(1), new Discount(DiscountUnit.PERCENT, 10), new co.uk.henry.promotion.ValidityPeriod(java.time.Period.parse("P0D"), java.time.Period.parse("P2D"))),
                new Promotion("P2", "1", new Quantity(1), new Discount(DiscountUnit.PERCENT, 5), new co.uk.henry.promotion.ValidityPeriod(java.time.Period.parse("P0D"), java.time.Period.parse("P2D")))
        ]

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "applied discount is returned"
        applicableDiscounts == 0.2
    }

    def "promotion is only applied when promotion quantity condition matches basket item's quantity"() {
        given: "items in the basket with quantity #basketItemQuantity"
        List<BasketItem> basketItems = [
                new BasketItem(new Item("1","someName", Unit.SINGLE, 4.00), basketItemQuantity),
        ]

        and: "an applicable promotion in the repository with min quantity #promotionMinQuantity"
        promotionRepository.getPromotions() >> [
                new Promotion("P1", "1", new Quantity(promotionMinQuantity), new Discount(DiscountUnit.PERCENT, 5), new co.uk.henry.promotion.ValidityPeriod(java.time.Period.parse("P0D"), java.time.Period.parse("P2D")))
        ]

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "applied discount is returned"
        applicableDiscounts == appliedDiscount

        where:
        basketItemQuantity | promotionMinQuantity | appliedDiscount
        1                  | 2                    | 0.0
        2                  | 2                    | 0.4
    }

    def "multiple promotions are applied for all items with matching quantity of items in the basket"() {
        given: "multiple items in the basket with single quantity"
        List<BasketItem> basketItems = [
                new BasketItem(new Item("1","someName", Unit.SINGLE, 4.00), 1),
                new BasketItem(new Item("2","someName", Unit.TIN, 5.00), 1),
                new BasketItem(new Item("3","someName", Unit.BOTTLE, 6.00), 1),
        ]

        and: "two applicable promotions"
        promotionRepository.getPromotions() >> [
                new Promotion("P1", "2", new Quantity(1), new Discount(DiscountUnit.PERCENT, 10), new co.uk.henry.promotion.ValidityPeriod(java.time.Period.parse("P0D"), java.time.Period.parse("P2D"))),
                new Promotion("P2", "3", new Quantity(1), new Discount(DiscountUnit.PERCENT, 5), new co.uk.henry.promotion.ValidityPeriod(java.time.Period.parse("P0D"), java.time.Period.parse("P2D")))
        ]

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "applied discount is returned"
        applicableDiscounts == 0.8
    }

    def "promotion valid from yesterday to next 7 days is applied"() {
        given: "multiple items in the basket with single quantity"
        List<BasketItem> basketItems = [
                new BasketItem(new Item("1","someName", Unit.SINGLE, 4.00), 1)
        ]

        and: "two applicable promotions"
        promotionRepository.getPromotions() >> [
                new Promotion("P1", "2", new Quantity(1), new Discount(DiscountUnit.PERCENT, 10),
                        new ValidityPeriod(Period.parse("-P1D"), Period.parse("P7D"))),
        ]

        when: "fetching cost with applied discounts"
        def applicableDiscounts = promotionService.getApplicableTotalDiscountFor(basketItems)

        then: "applied discount is returned"
        applicableDiscounts == 0.8
    }
}

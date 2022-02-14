package co.uk.henry.promotion

import spock.lang.Specification

class DiscountSpec extends Specification {

    def "Discount is applied correctly when it's a percentage"() {
        given: "a 10 percentage discount"
        Discount discount = new Discount(DiscountUnit.PERCENT, 10)

        when: "it is applied to the given amount"
        def discountedAmount = discount.applyTo(20)

        then:"discounted amount is returned"
        discountedAmount == 2.0
    }

    def "Discount is 0 when given price is less than or equal to 0 "() {
        given: "a 10 percentage discount"
        Discount discount = new Discount(DiscountUnit.PERCENT, 10)

        when: "it is applied to the given amount"
        def discountedAmount = discount.applyTo(price)

        then:"discounted amount is returned"
        discountedAmount == 0.0

        where:
        price | _
        0     | _
        -1    | _
    }
}

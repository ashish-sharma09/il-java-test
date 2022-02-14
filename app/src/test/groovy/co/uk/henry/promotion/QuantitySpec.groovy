package co.uk.henry.promotion

import spock.lang.Specification

class QuantitySpec extends Specification {

    def "applying quantity should return false if given quantity is less than or equal to 0"() {
        given:
        Quantity quantity = new Quantity(2)

        when:
        def quantityApplicable = quantity.appliesTo(givenQuantity)

        then:
        !quantityApplicable

        where:
        givenQuantity | _
        0             | _
        -1            | _
    }

    def "quantity should be applicable when its greater than or equal to minimum quantity and max is big enough"() {
        given:
        Quantity quantity = new Quantity(minQuantity)

        when:
        def quantityApplicable = quantity.appliesTo(itemQuantity)

        then:
        quantityApplicable == isApplicable

        where:
        minQuantity | itemQuantity | isApplicable
        2           | 1            | false
        2           | 3            | true
        2           | 2            | true
    }
}

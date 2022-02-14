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

    def "applying quantity should return false if given quantity is greater than 0 but does not fall between allowed quantity"() {
        given:
        Quantity quantity = new Quantity(2)

        when:
        def quantityApplicable = quantity.appliesTo(1)

        then:
        !quantityApplicable
    }

    def "applying quantity should return true if given quantity is greater than 0 and falls between allowed quantity"() {
        given:
        Quantity quantity = new Quantity(2)

        when:
        def quantityApplicable = quantity.appliesTo(3)

        then:
        quantityApplicable
    }
}

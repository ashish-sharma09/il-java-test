package co.uk.henry.promotion

import spock.lang.Specification

import java.time.LocalDate
import java.time.Period

class ValidityPeriodSpec extends Specification {

    def "true when given date falls within validity period"() {
        given: "period with validity of 5 days"
        ValidityPeriod validityPeriod = new ValidityPeriod(Period.parse("P2D"), Period.parse("P7D"))

        when:
        def isValid = validityPeriod.isValidFor(LocalDate.now().plusDays(3))

        then:
        isValid
    }

    def "true when given date starts at the same day as the validity from period"() {
        given: "period with validity of 5 days"
        ValidityPeriod validityPeriod = new ValidityPeriod(Period.parse("P2D"), Period.parse("P7D"))

        when:
        def isValid = validityPeriod.isValidFor(LocalDate.now().plusDays(2))

        then:
        isValid
    }

    def "true when given date starts today and validity is from yesterday"() {
        given: "period with validity of 5 days"
        ValidityPeriod validityPeriod = new ValidityPeriod(Period.parse("-P1D"), Period.parse("P7D"))

        when:
        def isValid = validityPeriod.isValidFor(LocalDate.now())

        then:
        isValid
    }
}

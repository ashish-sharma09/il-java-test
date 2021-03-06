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
        given: "period with validity of 7 days"
        ValidityPeriod validityPeriod = new ValidityPeriod(Period.parse("-P1D"), Period.parse("P7D"))

        when:
        def isValid = validityPeriod.isValidFor(LocalDate.now())

        then:
        isValid
    }

    def "true when given date is next month and validity is from 3 days to end of next month"() {
        given: "period with validity starting from 3 days to end of next month"
        ValidityPeriod validityPeriod = new ValidityPeriod(Period.parse("P3D"), Period.parse("P1M"))

        when:
        def isValid = validityPeriod.isValidFor(LocalDate.now().plusMonths(1))

        then:
        isValid
    }

    def "false when given date is in 2 months and validity is from 3 days to end of next month"() {
        given: "period with validity starting from 3 days to end of next month"
        ValidityPeriod validityPeriod = new ValidityPeriod(Period.parse("P3D"), Period.parse("P1M"))

        when:
        def isValid = validityPeriod.isValidFor(LocalDate.now().plusMonths(2))

        then:
        !isValid
    }
}

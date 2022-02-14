package co.uk.henry.promotion

import spock.lang.Specification
import spock.lang.Unroll

class PromotionServiceImplSpec extends Specification {

    private PromotionService promotionService;

    void setup() {
        promotionService = new PromotionServiceImpl()
    }

    @Unroll
    def "throw error when given basket items is #items"() {
        when: "basket items is null"
        promotionService.getCostWithApplicableDiscountsfor(items)

        then:
        thrown(IllegalArgumentException)

        where:
        items | _
        null  | _
        []    | _
    }
}

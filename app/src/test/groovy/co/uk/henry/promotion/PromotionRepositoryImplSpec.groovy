package co.uk.henry.promotion

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths
import java.time.Period

class PromotionRepositoryImplSpec extends Specification {

    private PromotionRepository promotionRepository;

    def "error when promotions json file does not exist"() {
        when: "a non-existing file is provided"
        Path path = Path.of("nonExistingFile")
        promotionRepository = new PromotionRepositoryImpl(path, new ObjectMapper())

        then:
        thrown(IllegalStateException)
    }

    def "promotions can be parsed and provided from the file"() {
        given: "a promotions json file"
        def jsonFilePath = Paths.get(this.class.classLoader.getResource("promotions.json").toURI())
        promotionRepository = new PromotionRepositoryImpl(jsonFilePath, new ObjectMapper())

        when: "promotions are fetched"
        def promotions = promotionRepository.getPromotions()

        then: "all promotions returned"
        promotions == [
                new Promotion (
                        "P1", PromotionType.MAIN, "1",
                        new Quantity(1,2), new Discount(DiscountType.MONEY, DiscountUnit.PERCENT, 50),
                        new ValidityPeriod(Period.parse("-P1D"), Period.parse("P7D"))
                ),
                new Promotion (
                        "P2", PromotionType.SUB, "2",
                        new Quantity(1,1), new Discount(DiscountType.MONEY, DiscountUnit.PERCENT, 50),
                        ValidityPeriod.FOREVER
                ),
                new Promotion (
                        "P3", PromotionType.MAIN, "1",
                        new Quantity(1,2), new Discount(DiscountType.MONEY, DiscountUnit.PERCENT, 50),
                        new ValidityPeriod(Period.parse("-P1D"), Period.parse("P7D"))
                )
        ]
    }
}
